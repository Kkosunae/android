package com.kkosunae.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kkosunae.GlobalApplication
import com.kkosunae.R
import com.kkosunae.databinding.ActivityLoginBinding
import com.kkosunae.model.KakaoRequest
import com.kkosunae.model.TokenItem
import com.kkosunae.network.KakaoLoginApi.postKakaoLogin
import com.kkosunae.viewmodel.MainViewModel
import kotlinx.coroutines.runBlocking

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    val mainViewModel: MainViewModel by viewModels()
    private lateinit var googleSignResultLauncher:ActivityResultLauncher<Intent>
    private lateinit var googleSignInClient:GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater);
        setContentView(binding.root)

        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOption)

        googleSignResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            result -> val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Log.d("loginActivity" ,"기타에러 :" + error.toString())
                        Toast.makeText(this, "기타 에러"+error.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else if (token != null) {
                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                Log.d("loginActivity" ,"token :" + token.toString() + ", token : " + token)
                val newToken = TokenItem(token.accessToken, token.refreshToken)
                mainViewModel.setCurrentToken(newToken)
                GlobalApplication.prefs.setString("accessToken", newToken.accessToken)
                GlobalApplication.prefs.setString("refreshToken", newToken.refreshToken)

                UserApiClient.instance.me { user, meerror ->
                    if (meerror != null) {
                        Log.d("loginActivity" , "meerror : " +meerror.toString())
                    } else {
                        runBlocking {
                            val userId = user?.id
                            val userEmail = user?.kakaoAccount?.email
                            val userName = user?.kakaoAccount?.name
                            Log.d("loginActivity" , "id : " +userId + "userEmail : " +userEmail + "userName : " +userName)
                            postKakaoLogin(KakaoRequest(userId.toString(),userName.toString(),userEmail.toString()))
                        }
                    }
                }
                val intent = Intent(this, MainActivity::class.java)
                mainViewModel.setIsLogin(true)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }

        binding.ivKakaoLoginActivity.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                Log.d("LoginActivity","loginWithKakaoTalk")
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                Log.d("LoginActivity","loginWithKakaoAccount")
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }

        binding.ivGoogleLoginActivity.setOnClickListener {
            val signIntent: Intent = googleSignInClient.signInIntent
            googleSignResultLauncher.launch(signIntent)

        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val email = account?.email.toString()
            var googletoken = account?.idToken.toString()
            var googletokenAuth = account?.serverAuthCode.toString()

            Log.e("Google account",email)
            Log.e("Google account",googletoken)
            Log.e("Google account", googletokenAuth)
            // TODO: 로그인 정보 회원인지 확인하는 작업 필요.
            val intent = Intent(this, MainActivity::class.java)
            mainViewModel.setIsLogin(true)
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        } catch (e: ApiException){
            Log.e("Google account","signInResult:failed Code = " + e.statusCode)
        }
    }
}
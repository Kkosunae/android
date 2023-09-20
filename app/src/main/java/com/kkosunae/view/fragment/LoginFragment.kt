package com.kkosunae.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kkosunae.R
import com.kkosunae.databinding.FragmentLoginBinding
import com.kkosunae.view.activity.MainActivity

class LoginFragment : Fragment(), View.OnClickListener{
    lateinit var binding: FragmentLoginBinding
    lateinit var mContext: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as MainActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.ivKakaoLogin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_kakao_login -> {
                Log.d("loginFragment" , "click login button")
                //
                val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                    if (error != null) {
                        when {
                            error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                                Toast.makeText(mContext, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                            }
                            error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                                Toast.makeText(mContext, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                            }
                            error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                                Toast.makeText(mContext, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                            }
                            error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                                Toast.makeText(mContext, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                            }
                            error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                                Toast.makeText(mContext, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                            }
                            error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                                Toast.makeText(mContext, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                            }
                            error.toString() == AuthErrorCause.ServerError.toString() -> {
                                Toast.makeText(mContext, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                            }
                            error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                                Toast.makeText(mContext, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                            }
                            else -> { // Unknown
                                Toast.makeText(mContext, "기타 에러", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else if (token != null) {
                        Toast.makeText(mContext, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(mContext)) {
                    UserApiClient.instance.loginWithKakaoTalk(mContext) { token, error ->
                        if (error != null) {
                            Log.e("LOGIN", "카카오톡으로 로그인 실패", error)

                            // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                            // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                                return@loginWithKakaoTalk
                            }

                            // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                            UserApiClient.instance.loginWithKakaoAccount(mContext, callback = callback)
                        } else if (token != null) {
                            Log.i("LOGIN", "카카오톡으로 로그인 성공 ${token.accessToken}")
                            val intent = Intent(mContext, MainActivity::class.java)
                            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                } else {

                    UserApiClient.instance.loginWithKakaoAccount(mContext, callback = callback)
                }
            }
        }
    }
    fun kakaoLogout() {
        // 로그아웃
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e("Hello", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            } else {
                Log.i("Hello", "로그아웃 성공. SDK에서 토큰 삭제됨")
            }
        }
    }
}
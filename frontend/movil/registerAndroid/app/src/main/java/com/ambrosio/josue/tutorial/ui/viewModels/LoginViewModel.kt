package com.ambrosio.josue.tutorial.ui.viewModels

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ambrosio.josue.tutorial.R
import com.ambrosio.josue.tutorial.RetrofitClient
import com.ambrosio.josue.tutorial.data.models.UsuarioModel
import com.ambrosio.josue.tutorial.data.servicios.UsuarioApi
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(
    private val context: Context
) : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val googleSignInClient: GoogleSignInClient
    private val _userLiveData = MutableLiveData<FirebaseUser?>()
    val userLiveData: LiveData<FirebaseUser?> = _userLiveData

    private val _inputsError = MutableLiveData<Boolean>()
    val inputsError: LiveData<Boolean> get() = _inputsError

    private val _authError = MutableLiveData<Boolean>()
    val authError: LiveData<Boolean> get() = _authError

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> get() = _loginSuccess

    private val _backendResponse = MutableLiveData<String>()
    val backendResponse: LiveData<String> get() = _backendResponse

    private val usuarioApi: UsuarioApi = RetrofitClient.usuarioApi

    init {
        // Configurar Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    fun signIn(): Intent {
        return googleSignInClient.signInIntent
    }

    fun handleSignInResult(intent: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
        try {
            val account = task.getResult(ApiException::class.java)!!
            Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            Log.w(TAG, "Google sign in failed", e)
            _authError.postValue(true)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    _userLiveData.postValue(auth.currentUser)
                    _loginSuccess.postValue(true)
                    auth.currentUser?.getIdToken(true)?.addOnCompleteListener { tokenTask ->
                        if (tokenTask.isSuccessful) {
                            val idToken = tokenTask.result?.token
                            idToken?.let { fetchBackendData(it) }
                        }
                    }
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    _authError.postValue(true)
                }
            }
    }

    fun fetchBackendData(accessToken: String) {
        val call = usuarioApi.obtenerDatosUsuarioPorToken("Bearer $accessToken")
        call.enqueue(object : Callback<UsuarioModel> {
            override fun onResponse(call: Call<UsuarioModel>, response: Response<UsuarioModel>) {
                if (response.isSuccessful) {
                    response.body()?.let { usuarioModel ->
                        // Procesar la respuesta exitosa
                        Log.d("InformacionProveedorVM", "Usuario encontrado: $usuarioModel")
                        // Aquí puedes manejar la lógica de tu aplicación con los datos del usuario
                    }
                } else {
                    Log.e("InformacionProveedorVM", "Error en la llamada: ${response.code()}")
                    // Manejar el error según tus necesidades
                }
            }

            override fun onFailure(call: Call<UsuarioModel>, t: Throwable) {
                Log.e("InformacionProveedorVM", "Error en la llamada: ${t.message}")
                // Manejar el error de red aquí
            }
        })
    }


    companion object {
        private const val TAG = "LoginViewModel"
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

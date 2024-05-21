<template>
  <div class="login">
    <div class="login__content">

      <div class="login__img">
        <img src="https://image.freepik.com/free-vector/code-typing-concept-illustration_114360-3581.jpg" alt="user login">
      </div>

      <div class="login__forms">
        <!-- login form -->
        <form @submit.prevent="login" class="login__register login-in" v-if="!isBlock">
          <h1 class="login__title">Sign In</h1>
          <span>type your account</span>
          <div class="login__box">
            <i class='bx bx-user login__icon'></i>
            <input type="text" placeholder="Login ID" class="login__input" v-model="signInState.form.loginId">
          </div>
          <div class="login__box">
            <i class='bx bx-lock login__icon'></i>
            <input type="password" placeholder="Password" class="login__input" v-model="signInState.form.password">
          </div>
          <a href="#" class="login__forgot">Forgot Password? </a>
          
          <button type="submit" class="login__button">Sign In</button>
          
          <div>
            <span class="login__account login__account--account">Don't Have an Account?</span>
            <span class="login__signin login__signin--signup sign-up" @click="changeBlock(true)">Sign Up</span>
          </div>
        </form>
        
        <!-- create account form -->
        <form @submit.prevent="register" class="login__create login-up" v-if="isBlock">
          <h1 class="login__title">Create Account</h1>
          <span>type your info for registration</span>
          <div class="login__box">
            <i class='bx bx-user login__icon'></i>
            <input type="text" placeholder="Login ID" class="login__input" v-model="signUpState.form.loginId">
          </div>
          
          <div class="login__box">
            <i class='bx bx-pencil login__icon'></i>
            <input type="text" placeholder="Nickname" class="login__input" v-model="signUpState.form.nickName">
          </div>
          
          <div class="login__box">
            <i class='bx bx-lock login__icon'></i>
            <input type="password" placeholder="Password" class="login__input" v-model="signUpState.form.password">
          </div>

          <div class="login__box">
            <i class='bx bx-lock login__icon'></i>
            <input type="password" placeholder="Password Confirm" class="login__input" v-model="signUpState.form.passwordConfirm">
          </div>
          
          <button type="submit" class="login__button">Sign Up</button>
          
          <div>
            <span class="login__account login__account--account">Already have an Account?</span>
            <span class="login__signup login__signup--signup sign-in" @click="changeBlock(false)">Sign In</span>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, ref, computed, watchEffect } from 'vue'
import axios from 'axios'
import router from '../router/index'
import store from '../store/index'

export default {
  name: 'Login',
  setup() {
    const isAuthenticated = computed(() => store.state.isAuthenticated);

    watchEffect(() => {
      if(isAuthenticated.value) {
        router.push({path: '/'});
      }
    })

    const isBlock = ref(false);

    /**
     * 로그인, 회원가입 창 전환
     * @param status 
     */
    const changeBlock = (status) => {
      isBlock.value = status;
    };

    const signUpState = reactive({
      form: {
        loginId: "",
        nickName: "",
        password: "",
        passwordConfirm: ""
      },
      message: "",
    });

    const signInState = reactive({
      form: {
        loginId: "",
        password: "",
      }
    })

    /**
     * 회원가입
     */
    const register = () => {
      axios.post('/api/join', signUpState.form).then((res) => {
        signUpState.message = res.data.message;
        window.alert(signUpState.message);
        changeBlock(false); // 로그인으로 이동

      }).catch((error) => {
        if(error.response && error.response.data) {
          signUpState.message = error.response.data.message;
        } else {
          signUpState.message = "사용할 수 없는 아이디 입니다.";
        }

        window.alert(signUpState.message);
      });
    };

    /**
     * 로그인
     */
    const login = () => {
      axios.post('/api/login', signInState.form).then((res) => {
        const accessToken = res.headers["authorization"];

        if(accessToken) {
          localStorage.setItem('accessToken', accessToken);
          window.alert('로그인 성공!');
          store.dispatch('initializeAuthentication');
          router.push({path: '/'});
        } else {
          window.alert('로그인 토큰을 받아오는데 실패했습니다.');
        }
      }).catch(() => {
        window.alert('아이디 혹은 비밀번호가 일치하지 않습니다..');
      })
    };

    return {
      isAuthenticated,
      isBlock,
      changeBlock,
      signUpState,
      register,
      signInState,
      login
    }
  }
}
  
</script>

<style>
  @import '../assets/login.css'
</style>
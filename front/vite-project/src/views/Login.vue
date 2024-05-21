<template>
  <div class="login">
    <div class="login__content">

      <div class="login__img">
        <img src="https://image.freepik.com/free-vector/code-typing-concept-illustration_114360-3581.jpg" alt="user login">
      </div>

      <div class="login__forms">
        <!-- login form -->
        <form class="login__register login-in" v-if="!isBlock">
          <h1 class="login__title">Sign In</h1>
          <span>type your account</span>
          <div class="login__box">
            <i class='bx bx-user login__icon'></i>
            <input type="text" placeholder="Login ID" class="login__input">
          </div>
          <div class="login__box">
            <i class='bx bx-lock login__icon'></i>
            <input type="password" placeholder="Password" class="login__input">
          </div>
          <a href="#" class="login__forgot">Forgot Password? </a>
          
          <a href="#" class="login__button">Sign In</a>
          
          <div>
            <span class="login__account login__account--account">Don't Have an Account?</span>
            <span class="login__signin login__signin--signup sign-up" @click="changeBlock(true)">Sign Up</span>
          </div>
        </form>
        
        <!-- create account form -->
        <form class="login__create login-up" v-if="isBlock">
          <h1 class="login__title">Create Account</h1>
          <span>type your info for registration</span>
          <div class="login__box">
            <i class='bx bx-user login__icon'></i>
            <input type="text" placeholder="Login ID" class="login__input">
          </div>
          
          <div class="login__box">
            <i class='bx bx-pencil login__icon'></i>
            <input type="text" placeholder="Nickname" class="login__input">
          </div>
          
          <div class="login__box">
            <i class='bx bx-lock login__icon'></i>
            <input type="password" placeholder="Password" class="login__input">
          </div>

          <div class="login__box">
            <i class='bx bx-lock login__icon'></i>
            <input type="password" placeholder="Password Confirm" class="login__input">
          </div>
          
          <a href="#" class="login__button">Sign Up</a>
          
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
import { ref } from 'vue';

export default {
  name: 'Login',
  setup() {
    const isBlock = ref(false);

    /**
     * 로그인, 회원가입 창 전환
     * @param status 
     */
    const changeBlock = (status) => {
      isBlock.value = status;
    };

    return {
      isBlock,
      changeBlock,
    }
  }
}
  
</script>

<style>
  @import '../assets/login.css'
</style>
<template>
  <div class="login">
    <div class="login__content">
      <div class="login__img">
        <img
          src="https://image.freepik.com/free-vector/code-typing-concept-illustration_114360-3581.jpg"
          alt="user login"
        />
      </div>

      <div class="login__forms">
        <!-- login form -->
        <form
          @submit.prevent="login"
          class="login__register login-in"
          v-if="!isBlock"
        >
          <h1 class="login__title">Sign In</h1>
          <span>type your account</span>
          <div class="login__box">
            <i class="bx bx-user login__icon"></i>
            <input
              type="text"
              placeholder="Login ID"
              class="login__input"
              v-model="signInState.form.loginId"
            />
          </div>
          <div class="login__box">
            <i class="bx bx-lock login__icon"></i>
            <input
              type="password"
              placeholder="Password"
              class="login__input"
              v-model="signInState.form.password"
            />
          </div>
          <a href="#" class="login__forgot">Forgot Password? </a>

          <button type="submit" class="login__button">Sign In</button>

          <div>
            <span class="login__account login__account--account"
              >Don't Have an Account?</span
            >
            <span
              class="login__signin login__signin--signup sign-up"
              @click="changeBlock(true)"
              >Sign Up</span
            >
          </div>
        </form>

        <!-- create account form -->
        <form
          @submit.prevent="register"
          class="login__create login-up"
          v-if="isBlock"
        >
          <h1 class="login__title">Create Account</h1>
          <span>type your info for registration</span>
          <div class="login__box">
            <i class="bx bx-user login__icon"></i>
            <input
              type="text"
              placeholder="Login ID"
              class="login__input"
              v-model="signUpState.form.loginId"
            />
          </div>

          <div class="login__box">
            <i class="bx bx-pencil login__icon"></i>
            <input
              type="text"
              placeholder="Nickname"
              class="login__input"
              v-model="signUpState.form.nickname"
            />
          </div>
<<<<<<< HEAD

          <div class="login__box">
            <i class="bx bx-lock login__icon"></i>
            <input
              type="password"
              placeholder="Password"
              class="login__input"
              v-model="signUpState.form.password"
            />
          </div>

          <div class="login__box">
            <i class="bx bx-lock login__icon"></i>
            <input
              type="password"
              placeholder="Password Confirm"
              class="login__input"
              v-model="signUpState.form.passwordConfirm"
            />
          </div>
          <small v-if="!isMatched" class="password__match"
            >비밀번호가 일치하지 않습니다.</small
          >

=======
          <small v-if="!isMatched" class="password__match">비밀번호가 일치하지 않습니다.</small>
          
>>>>>>> 1411b4c (feat: 회원가입, 로그인 시 유효성 검증 추가)
          <button type="submit" class="login__button">Sign Up</button>

          <div>
            <span class="login__account login__account--account"
              >Already have an Account?</span
            >
            <span
              class="login__signup login__signup--signup sign-in"
              @click="changeBlock(false)"
              >Sign In</span
            >
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, ref, computed, watchEffect } from 'vue';
import axios from 'axios';
import instance from '../api/axios';
import router from '../router/index';
import store from '../store/index';

export default {
  name: 'Login',
  setup() {
    const isAuthenticated = computed(() => store.state.isAuthenticated);
<<<<<<< HEAD
    const isMatched = ref(true);
    const signUpState = reactive({
      form: {
        loginId: '',
        nickname: '',
        password: '',
        passwordConfirm: '',
      },
      message: '',
=======
    const isBlock = ref(false);
    const isMatched = ref(true);
    const signUpState = reactive({
      form: {
        loginId: "",
        nickName: "",
        password: "",
        passwordConfirm: ""
      },
      message: "",
>>>>>>> 1411b4c (feat: 회원가입, 로그인 시 유효성 검증 추가)
    });

    const signInState = reactive({
      form: {
<<<<<<< HEAD
        loginId: '',
        password: '',
      },
    });

    /**
     * 로그인한 사용자가 다시 로그인 할 수 없도록 라우팅
     */
    watchEffect(() => {
      if (isAuthenticated.value) {
        router.push({ path: '/' });
      }
    });

    /**
     * 로그인, 회원가입 창 전환
     * @param status
     */
    const changeBlock = (status) => {
      isBlock.value = status;
    };

    /**
     * 비밀번호 일치 여부 확인
     * @param status
     */
    const matchedPassword = (status) => {
      isMatched.value = status;
    };
=======
        loginId: "",
        password: "",
      }
    })
>>>>>>> 1411b4c (feat: 회원가입, 로그인 시 유효성 검증 추가)

    /**
     * 로그인한 사용자가 다시 로그인 할 수 없도록 라우팅
     */
    watchEffect(() => {
      if(isAuthenticated.value) {
        router.push({path: '/'});
      }
    })

    /**
     * 로그인, 회원가입 창 전환
     * @param status 
     */
    const changeBlock = (status) => {
      isBlock.value = status;
    };

    /**
     * 비밀번호 일치 여부 확인
     * @param status 
     */
    const matchedPassword = (status) => {
      isMatched.value = status;
    }

    /**
     * 회원가입
     */
    const register = () => {
<<<<<<< HEAD
      const isFilled =
        signUpState.form.loginId &&
        signUpState.form.nickname &&
        signUpState.form.password &&
        signUpState.form.passwordConfirm;

      if (!isFilled) {
=======
      const isFilled = signUpState.form.loginId && signUpState.form.nickName && 
      signUpState.form.password && signUpState.form.passwordConfirm;

      if(!isFilled) {
>>>>>>> 1411b4c (feat: 회원가입, 로그인 시 유효성 검증 추가)
        window.alert('모든 필드를 작성해주세요.');
        return;
      }

<<<<<<< HEAD
      if (signUpState.form.password !== signUpState.form.passwordConfirm) {
        matchedPassword(false);
        return;
      } else {
        axios
          .post('/api/join', signUpState.form)
          .then((res) => {
            signUpState.message = res.data.message;
            window.alert(signUpState.message);
            changeBlock(false); // 로그인으로 이동
          })
          .catch((error) => {
            if (error.response && error.response.data) {
              signUpState.message = error.response.data.message;
            } else {
              signUpState.message = '사용할 수 없는 아이디 입니다.';
            }

            window.alert(signUpState.message);
          });
=======
      if(signUpState.form.password !== signUpState.form.passwordConfirm) {
        matchedPassword(false);
        return;
      } else {
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
>>>>>>> 1411b4c (feat: 회원가입, 로그인 시 유효성 검증 추가)
      }
    };

    /**
     * 로그인
     */
    const login = () => {
      const isFilled = signInState.form.loginId && signInState.form.password;
<<<<<<< HEAD
=======

      if(!isFilled) {
        window.alert('모든 필드를 작성해주세요.');
        return;
      }

      axios.post('/api/login', signInState.form).then((res) => {
        const accessToken = res.headers["authorization"];
>>>>>>> 1411b4c (feat: 회원가입, 로그인 시 유효성 검증 추가)

      if (!isFilled) {
        window.alert('모든 필드를 작성해주세요.');
        return;
      }

      axios
        .post('/api/login', signInState.form)
        .then((res) => {
          const accessToken = res.headers['authorization'];

          if (accessToken) {
            localStorage.setItem('accessToken', accessToken);
            window.alert('로그인 성공!');

            instance
              .get('/api/user')
              .then(() => {
                // 초기화 작업 수행
                store.dispatch('initializeAuthentication');
                router.push({ path: '/' });
              })
              .catch((error) => {
                console.error('사용자 정보를 조회하는데 실패했습니다.', error);
              });
          } else {
            window.alert('로그인 토큰을 받아오는데 실패했습니다.');
          }
        })
        .catch(() => {
          window.alert('아이디 혹은 비밀번호가 일치하지 않습니다..');
        });
    };

    return {
      isAuthenticated,
      isBlock,
      isMatched,
      signUpState,
      signInState,
      changeBlock,
      matchedPassword,
      register,
<<<<<<< HEAD
      login,
    };
  },
};
=======
      login
    }
  }
}
  
>>>>>>> 1411b4c (feat: 회원가입, 로그인 시 유효성 검증 추가)
</script>

<style>
@import '../assets/login.css';
</style>

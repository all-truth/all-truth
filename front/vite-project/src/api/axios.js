import axios from 'axios';
import store from '../store/index';

// axios 인스턴스 생성
const instance = axios.create({
  // Vite 개발 서버의 주소로 설정
  baseURL: '/',
});

// request 인터셉터 추가
instance.interceptors.request.use(
  (config) => {
    const accessToken = localStorage.getItem('accessToken');

    if (accessToken) {
      config.headers.Authorization = accessToken;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// response 인터셉터 추가
instance.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    const { status } = error.response;

    // 토큰이 유효하지 않을 경우 로그아웃
    if(status === 401 || status === 403) {
      store.dispatch('logout');
    }

    return Promise.reject(error);
  }
);

export default instance;

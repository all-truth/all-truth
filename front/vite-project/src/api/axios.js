import axios from 'axios'
import store from '../store/index'

// axios 인스턴스 생성
const instance = axios.create({
  baseURL: 'http://localhost:8080',
});

// request 인터셉터 추가
instance.interceptors.request.use((config) => {
  const accessToken = localStorage.getItem('accessToken');

  if(accessToken) {
    config.headers.Authorization = accessToken;
  }

  return config;
}).catch((error) => {
  return Promise.reject(error);
});

// response 인터셉터 추가
instance.interceptors.response.use(
  response => {
    return response;
  },
  error => {
    const { status } = error.response;

    if(status === 401) {
      store.dispatch('logout');
    }

    return Promise.reject(error);
  }
);

export default instance;

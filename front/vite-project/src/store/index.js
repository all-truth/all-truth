import { createStore } from 'vuex'
import axios from '../api/axios'

export default createStore({
  state: {
    isAuthenticated: false,
    reviews: []
  },
  mutations: {
    setAuthentication(state, status) {
      state.isAuthenticated = status;
    },
    logout(state) {
      state.isAuthenticated = false;
    },
    setReviews(state, reviews) {
      state.reviews = reviews;
    },
    addReview(state, review) {
      state.reviews.push(review);
    },
  },
  actions: {
    initializeAuthentication({ commit }) {
      const accessToken = localStorage.getItem('accessToken');

      if(accessToken) {
        commit('setAuthentication', true);
      } else {
        commit('setAuthentication', false);
      }
    },
    logout({ commit }) {
      localStorage.removeItem('accessToken');
      commit('setAuthentication', false);
    },
    fetchReviews({ commit }) {
      axios.get('/api/reviews').then((res) => {
        commit('setReviews', res.data);
      }).catch((error) => {
        console.log('리뷰 조회 중 에러가 발생했습니다. ', error);
      });
    },
    addReview({ commit }, review) {
      commit('addReview', review);
    },
  },
  getters: {
    reviews: (state) => state.reviews,
  },
});

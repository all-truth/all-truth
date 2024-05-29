import { createStore } from 'vuex'
import axios from '../api/axios'

export default createStore({
  state: {
    isAuthenticated: false,
    reviews: [],
    searchText: '',
    searchResults: [],
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
    setSearchText(state, text) {
      state.searchText = text;
    },
    setSearchResults(state, results) {
      state.searchResults = results;
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
    setSearchText({ commit }, text) {
      commit('setSearchText', text);
    },
    setSearchResults({ commit }, results) {
      commit('setSearchResults', results);
    },
  },
  getters: {
    reviews: (state) => state.reviews,
  },
});

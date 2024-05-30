import { createStore } from 'vuex'
import axios from '../api/axios'
import instance from '../api/axios';
import createPersistedState from 'vuex-persistedstate';

export default createStore({
  plugins: [
    createPersistedState({
      key: 'my-app-state',
      paths: ['currentFilter', 'reviews'],
    }),
  ],

  state: {
    isAuthenticated: false,
    reviews: [],
    searchText: '',
    searchResults: [],
    currentFilter: null,
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
    setCurrentFilter(state, filter) {
      state.currentFilter = filter;
    }
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
        commit('setCurrentFilter', 'all');
      }).catch((error) => {
        console.log('리뷰 조회 중 에러가 발생했습니다. ', error);
      });
    },
    fetchUserReviews({ commit }) {
      instance.get('/api/reviews/user/my').then((res) => {
        commit('setReviews', res.data);
        commit('setCurrentFilter', 'my-reviews');
      }).catch((error) => {
        console.log('사용자 리뷰 조회 중 에러가 발생했습니다. ', error);
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
    navigateToHome({ commit }) {
      commit('setCurrentFilter', null);
    }
  },
  getters: {
    reviews: (state) => state.reviews,
    currentFilter: (state) => state.currentFilter,
  },
});

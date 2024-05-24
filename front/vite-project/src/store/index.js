import { createStore } from 'vuex'

export default createStore({
  state: {
    isAuthenticated: false,
    user: null,
  },
  mutations: {
    setAuthentication(state, status) {
      state.isAuthenticated = status;
    },
    logout(state) {
      state.isAuthenticated = false;
    },
    setUser(state, user) {
      state.user = user;
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
      commit('setUser', null);
      commit('setAuthentication', false);
    },
    setUser({ commit }, user) {
      commit('setUser', user);
    }
  }
})

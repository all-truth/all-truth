import { createStore } from 'vuex'

export default createStore({
  state: {
    isAuthenticated: false,
  },
  mutations: {
    setAuthentication(state, status) {
      state.isAuthenticated = status;
    },
    logout(state) {
      state.isAuthenticated = false;
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
  }
})

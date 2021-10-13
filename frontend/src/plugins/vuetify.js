import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';
import colors from 'vuetify/lib/util/colors';

Vue.use(Vuetify);

export default new Vuetify({
    theme: {
        themes: {
          light: {
            primary: '206a5d',
            secondary: 'F58634', //colors.deepOrange,
            accent: 'FFCC29',
            error: 'd62828',
          }
        },
    },
});

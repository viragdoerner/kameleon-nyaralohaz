import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';
import colors from 'vuetify/lib/util/colors';

Vue.use(Vuetify);

export default new Vuetify({
    theme: {
        themes: {
          light: {
            cgreen: '206a5d',
            clightgreen: '81B214',
            cbggreen: 'D4ECDD',
            corange: 'F58634', //colors.deepOrange,
            cyellow: 'FFCC29',
            error: 'd62828',
          }
        },
    },
});

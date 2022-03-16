<template>
  <v-footer padless>
    <v-card flat tile width="100%" class="corange text-center">
      <v-card-text v-if="$store.getters.getRole != 'Admin'">
        <transition-group class="footer-elements">
          <v-btn
            key="1"
            class="mx-4 footer-element"
            icon
            @click="expand_address = !expand_address"
          >
            <v-icon size="24px" color="green darken-3"> fa-home </v-icon>
          </v-btn>

          <p
            key="2"
            d-flex
            style="display: inline"
            v-show="expand_address"
            class="white--text footer-element"
          >
            {{ weekendhouse.address }}
          </p>

          <v-btn
            key="3"
            transition="slide-x-transition"
            class="mx-4 footer-element"
            icon
            @click="expand_phone = !expand_phone"
          >
            <v-icon d-flexsize="24px" color="purple darken-3">
              fa-phone
            </v-icon>
          </v-btn>
          <p
            key="4"
            d-flex
            style="display: inline"
            v-show="expand_phone"
            class="white--text footer-element"
          >
            {{ weekendhouse.phone_number }}
          </p>
          <v-btn
            key="5"
            class="mx-4 footer-element"
            icon
            :href="weekendhouse.facebook"
            target="blank"
          >
            <v-icon size="24px" color="blue darken-3"> fa-facebook </v-icon>
          </v-btn>
          <v-btn
            key="6"
            class="mx-4 footer-element"
            icon
            :href="'mailto:' + weekendhouse.email"
          >
            <v-icon size="24px" color="red darken-3"> fa-envelope </v-icon>
          </v-btn>
        </transition-group>
      </v-card-text>

      <v-card-text
        class="pt-16"
        v-if="currentRouteName == 'Home' && $store.getters.loggedIn && $store.getters.isAdmin"
      >
        <v-row justify="center">
          <v-col cols="12" sm="6" md="3">
            <v-text-field
              solo
              v-model="weekendhouse.address"
              v-on:change="saveWeekendhouse()"
              prepend-icon="fa-home"
            ></v-text-field>
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-text-field
              solo
              v-model="weekendhouse.phone_number"
              v-on:change="saveWeekendhouse()"
              prepend-icon="fa-phone"
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row justify="center">
          <v-col cols="12" sm="6" md="3">
            <v-text-field
              solo
              v-model="weekendhouse.facebook"
              v-on:change="saveWeekendhouse()"
              prepend-icon="fa-facebook"
            ></v-text-field>
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-text-field
              solo
              v-model="weekendhouse.email"
              v-on:change="saveWeekendhouse()"
              prepend-icon="fa-envelope"
            ></v-text-field>
          </v-col>
        </v-row>
      </v-card-text>

      <v-divider></v-divider>

      <v-card-text class="white--text">
        {{ new Date().getFullYear() }} — <strong>Kaméleon Nyaralóház</strong>
      </v-card-text>
    </v-card>
  </v-footer>
</template>

<script>
import ApiService from "../services/api.service"
export default {
  name: "CFooter",
  data: () => ({
    expand_address: false,
    expand_phone: false,
    weekendhouse: {},
  }),
  computed: {
    currentRouteName() {
      return this.$route.name;
    },
  },
  mounted() {
    this.getWeekendhouse();
  },
  methods: {
    getWeekendhouse() {
      ApiService.getPublicData( "weekendhouse")
        .then((response) => {
          if (!response.data) throw "empty list";
          this.weekendhouse = response.data;
        })
        .catch((error) => {
          alert(error);
        });
    },
    saveWeekendhouse() {
      ApiService.putToSecuredApi( "weekendhouse", this.weekendhouse)
        .then((response) => {
          if (!response.data) throw "empty list";
          this.weekendhouse = response.data;
           this.$store.commit("showMessage", {
            active: true,
            color: "cgreen",
            message: "Sikeres mentés",
          });
        })
        .catch((error) => {
           this.$store.commit("showMessage", {
            active: true,
            color: "error",
            message: "Sikertelen módosítás",
          });
        });
    },
  },
};
</script>

<style>
.footer-element {
  transition: all 1s;
}
.footer-elements-enter,
.footer-elements-leave-to,
.footer-elements-leave,
.footer-elements-enter-to {
  opacity: 0;
  transform: translateX(30px);
}

.footer-elements-leave-active,
.footer-elements-enter-active {
  position: absolute;
}
</style>

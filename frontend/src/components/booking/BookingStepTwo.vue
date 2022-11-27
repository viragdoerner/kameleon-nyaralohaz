<template>
  <v-card elevation="0">
    <v-card-text class="pa-0 pa-sm-4">
      <v-form ref="userDataForm" v-model="valid" lazy-validation class="col-12 d-flex flex-wrap px-0">

          <v-col class="col-12 col-sm-6 px-0 px-sm-3">
            <v-text-field
              v-model="user.lastname"
              label="Vezetéknév"
              regular
              color="cgreen"
              :rules="[rules.required]"
              autocomplete="family-name"
            ></v-text-field>
          </v-col>
          <v-col class="col-12 col-sm-6 px-0 px-sm-3">
            <v-text-field
              v-model="user.firstname"
              label="Keresztnév"
              regular
              color="cgreen"
              :rules="[rules.required]"
              autocomplete="given-name"
            ></v-text-field>
          </v-col>
          <v-col class="col-12 col-sm-6 px-0 px-sm-3">
              <v-text-field
                v-model="user.phonenumber"
                label="Telefonszám"
                regular
                color="cgreen"
                :rules="[rules.required]"
                autocomplete="tel"
              ></v-text-field>
          </v-col>
          <v-col class="col-12 col-sm-6 px-0 px-sm-3">
            <v-text-field
              :value="user.email"
              label="E-mail-cím"
              autocomplete="email"
              regular
              color="cgreen"
              disabled
            ></v-text-field>
          </v-col>
         
      </v-form>
    </v-card-text>
    <v-card-actions class="col-12 d-flex justify-end">
      <v-btn
        color="clightgreen"
        @click="finishStepTwo"
        :disabled="!valid"
        class="white--text"
        :x-large="$vuetify.breakpoint.mobile"
      >
        Tovább
      </v-btn>
      <v-btn text @click="moveBack" :x-large="$vuetify.breakpoint.mobile"> Vissza </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import ApiService from "../../services/api.service";

export default {
  name: "CBookingStepTwo",
  props: [],
  components: {},
  data: () => ({
    valid: false,
    user: {},
    rules: {
      required: (value) => !!value || "Kötelező mező."
    },
  }),
  methods: {
    getCurrentUser() {
      ApiService.GET("user/current")
        .then((response) => {
          if (!response.data) throw "empty list";
          this.user = response.data;
        })
        .catch((error) => {
          this.$store.commit("showMessage", {
            active: true,
            color: "error",
            message: "Hiba történt a felhasználó adatainak lekérésénél",
          });
        });
    },
    finishStepTwo() {
      ApiService.PUT("user", this.user)
        .then((response) => {
          this.$emit("next-step", this.user);
        })
        .catch((error) => {
          this.$store.commit("showMessage", {
            active: true,
            color: "error",
            message: "Hiba történt a felhasználó adatainak frissítésénél",
          });
        });
    },
    moveBack() {
      this.$emit("back-step");
    },
  },
  computed: {},
  mounted() {
    this.getCurrentUser();
  },
};
</script>

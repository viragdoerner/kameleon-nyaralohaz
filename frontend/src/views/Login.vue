<template>
  <div>
    <v-row class="mt-5 ml-5">
      <v-btn x-large icon color="white" to="/">
        <v-icon x-large >
          mdi-arrow-left
        </v-icon>
      </v-btn>
    </v-row>
    <v-container class="d-flex align-center justify-center mt-10 ptb-5">
      <v-card persistent class="col-12 col-sm-8 col-md-6 mt-5 pa-0">
        <div>
          <v-tabs
            v-model="tab"
            show-arrows
            background-color="cyellow accent-4"
            icons-and-text
            dark
            grow
          >
            <v-tabs-slider color="cyellow darken-2"></v-tabs-slider>
            <v-tab v-for="i in tabs" :key="i.name">
              <h3 class="">{{ i.name }}</h3>
            </v-tab>
            <v-tab-item>
              <v-card class="px-4">
                <v-card-text>
                  <v-form ref="loginForm" v-model="valid" lazy-validation>
                    <v-row>
                      <v-col cols="12">
                        <v-text-field
                          v-model="loginEmail"
                          :rules="[rules.email, rules.required]"
                          label="E-mail"
                          autocomplete="email"
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12">
                        <v-text-field
                          v-model="loginPassword"
                          :append-icon="show1 ? 'eye' : 'eye-off'"
                          :rules="[rules.required, rules.min8]"
                          :type="show1 ? 'text' : 'password'"
                          name="password"
                          autocomplete="password"
                          label="Jelszó"
                          hint="Minimum 8 karakter hosszú"
                          counter
                          @click:append="show1 = !show1"
                        ></v-text-field>
                      </v-col>
                      <v-col class="d-flex" cols="12" sm="6" xsm="12"> </v-col>
                      <v-spacer></v-spacer>
                      <v-col class="d-flex" cols="12" sm="3" xsm="12" align-end>
                        <v-btn
                          x-large
                          block
                          :disabled="!valid"
                          color="success"
                          @click="validateLogin"
                        >
                          Login
                        </v-btn>
                      </v-col>
                    </v-row>
                  </v-form>
                </v-card-text>
              </v-card>
            </v-tab-item>
            <v-tab-item>
              <v-card class="px-4">
                <v-card-text>
                  <v-form ref="registerForm" v-model="valid" lazy-validation>
                    <v-row>
                      <v-col cols="12">
                        <v-text-field
                          v-model="email"
                          :rules="[rules.email, rules.required]"
                          label="E-mail"
                          autocomplete="email"
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12">
                        <v-text-field
                          v-model="password"
                          :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                          :rules="[rules.required, rules.min8]"
                          :type="show1 ? 'text' : 'password'"
                          name="new-password"
                          autocomplete="new-password"
                          label="Jelszó"
                          hint="Minimum 8 karakter hosszú"
                          counter
                          @click:append="show1 = !show1"
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12">
                        <v-text-field
                          block
                          v-model="verify"
                          :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                          :rules="[rules.required, passwordMatch]"
                          :type="show1 ? 'text' : 'password'"
                          name="input-10-1"
                          autocomplete="verify-password"
                          label="Jelszó megerősítése"
                          counter
                          @click:append="show1 = !show1"
                        ></v-text-field>
                      </v-col>
                      <v-spacer></v-spacer>
                      <v-col class="d-flex ml-auto" cols="12" sm="3" xsm="12">
                        <v-btn
                          x-large
                          block
                          :disabled="!valid"
                          color="success"
                          @click="validateRegister"
                          >Register</v-btn
                        >
                      </v-col>
                    </v-row>
                  </v-form>
                </v-card-text>
              </v-card>
            </v-tab-item>
          </v-tabs>
        </div>
        <v-overlay :absolute="absolute" :value="loading" color="cgreen">
          <v-progress-circular indeterminate size="64"></v-progress-circular>
        </v-overlay>
      </v-card>

      
    </v-container>
    <v-row class="col-12 d-flex justify-center">
      <v-alert
        icon="mdi-shield-lock-outline"
        prominent
        text
        type="info"
        dismissible
        color="cgreen"
        transition="scale-transition"
        class="col-12 col-sm-8 col-md-4 ma-5"
        v-if="showAlert"
      >
        Küldtünk egy megerősítő e-mailt a megadott e-mail-címedre. A
        regisztráció befejezéséhez kérlek kattints az ott található linkre.
      </v-alert>
    </v-row>
  </div>
</template>

<script>
export default {
  name: "CLogin",
  computed: {
    passwordMatch() {
      return () =>
        this.password === this.verify || "A jelszavaknak egyeznie kell";
    },
  },
  methods: {
    validateRegister() {
      if (this.$refs.registerForm.validate()) {
        this.loading = true;
        var registerForm = {
          email: this.email,
          password: this.password,
        };
        this.$store
          .dispatch("auth/register", registerForm)
          .then(() => {
            this.loading = false;
            this.showAlert = true;
            this.tab = 0;
            this.$store.commit("showMessage", {
              active: true,
              color: "success",
              message: "Sikeres regisztráció",
            });
          })
          .catch((error) => {
            this.loading = false;
            this.$store.commit("showMessage", {
              active: true,
              color: "error",
              message: "Sikertelen bejelentkezés",
            });
          });
      }
    },
    validateLogin() {
      if (this.$refs.loginForm.validate()) {
        this.loading = true;
        var loginForm = {
          email: this.loginEmail,
          password: this.loginPassword,
        };
        this.$store
          .dispatch("auth/login", loginForm)
          .then(() => {
            this.loading = false;
            this.$router.push("/");
            this.showAlert = false;
          })
          .catch((error) => {
            this.loading = false;
            this.$store.commit("showMessage", {
              active: true,
              color: "error",
              message: "Sikertelen bejelentkezés",
            });
          });
      }
    },
    reset() {
      this.$refs.form.reset();
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    },
  },
  data: () => ({
    absolute: true,
    loading: false,
    showAlert: false,
    tab: 0,
    tabs: [{ name: "BEJELENTKEZÉS" }, { name: "REGISZTRÁLÁS" }],
    valid: true,
    email: "",
    password: "",
    verify: "",
    loginPassword: "",
    loginEmail: "",
    show1: false,
    rules: {
      email: (value) => /.+@.+\..+/.test(value) || "Érvénytelen e-mail",
      required: (value) => !!value || "Kötelező mező.",
      min8: (value) => (value && value.length >= 8) || "Minimum 8 karakter",
    },
  }),
};
</script>

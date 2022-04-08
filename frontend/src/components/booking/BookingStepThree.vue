<template>
  <v-card elevation="0">
    <v-card-text>
      <v-row>
        <v-col>
          <v-text-field
            :value="user.lastname"
            label="Vezetéknév"
            outlined color="cgreen"
            dense
            readonly
          ></v-text-field>
        </v-col>
        <v-col>
          <v-text-field
            :value="user.firstname"
            label="Keresztnév"
            outlined color="cgreen"
            dense
            readonly
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-text-field
            :value="user.phonenumber"
            label="Telefonszám"
            outlined color="cgreen"
            dense
            readonly
          ></v-text-field>
        </v-col>
        <v-col>
          <v-text-field
            :value="user.email"
            label="E-mail-cím"
            outlined color="cgreen"
            dense
            readonly
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-textarea v-model="comment" label="Megjegyzés" solo></v-textarea>
        </v-col>
      </v-row>
    </v-card-text>
    <v-card-actions>
      <v-btn color="clightgreen" @click="finishStepThree" class="white--text">
        Foglalás elküldése
      </v-btn>
      <v-btn text @click="moveBack"> Vissza </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
//import ApiService from "../../services/api.service";

export default {
  name: "CBookingStepTwo",
  props: [],
  components: {},
  data: () => ({
    user: {},
    comment: "",
  }),
  methods: {
    finishStepThree() {
      this.$emit("next-step");
    },
    moveBack() {
     this.$emit("back-step");
    },
  },
  computed: {},
  mounted() {},
};
</script>

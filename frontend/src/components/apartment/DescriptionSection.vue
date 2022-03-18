<template>
  <v-container class="d-flex flex-column flex-md-row px-md-16 px-2 col-12 col-sm-9 col-md-10" fluid>
    <v-card class="pa-0 pa-md-10 ma-md-10 ms-1 transparentCard col-md-6 col-12" elevation="0">
      <v-card-title class="text-h3 zabatana cgreen--text">{{
        apartment.name
      }}</v-card-title>
      <v-card-text>
        <v-row class="pb-2" v-if="$store.getters.loggedIn && $store.getters.isAdmin">
          <v-text-field
            color="cgreen"
            label="Apartman neve"
            v-model="apartment.name"
            v-on:change="onUpdateApartment"
            maxlength="50"
            required
          ></v-text-field>
        </v-row>
        <v-row class="pb-2" v-if="$store.getters.loggedIn && $store.getters.isAdmin">
          <v-text-field
            color="cgreen"
            label="Ár Ft/éj"
            v-model="apartment.price"
            v-on:change="onUpdateApartment"
            type="number"
            required
          ></v-text-field>
        </v-row>
        <h4 v-if="!$store.getters.isAdmin">{{ apartment.price }} Ft/éj</h4>
        <c-rich-text-editor
          class="pt-5"
          :description="apartment.description"
          v-on:update-description="onUpdateDescription"
        ></c-rich-text-editor>
      </v-card-text>
    </v-card>
    <v-card class="pa-md-10 pa-4 ma-md-10  ma-0 mt-5 mt-md-10 col-12 col-sm-8 col-md-6 align-self-center ">
      <v-card-text>
        <v-row>
          <v-text-field
            v-model="dateRangeText"
            label="Date range"
            prepend-icon="mdi-calendar"
            readonly
          ></v-text-field>
        </v-row>
        <v-row>
          <v-combobox filled solo></v-combobox>
        </v-row>
        <v-row>
          <v-combobox filled solo></v-combobox>
        </v-row>
        <v-row>
          <v-btn elevation="2" x-large color="corange" class="white--text"
            >FOGLALÁS
          </v-btn>
        </v-row>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import CRichTextEditor from "../home/RichTextEditor.vue";
export default {
  name: "CReserveSection",
  data: () => ({
    dateRangeText: null,
    color: "#206a5d",
    font: "Roboto",
    apartment: {},
  }),
  methods: {
    onUpdateDescription(newDescription) {
      this.apartment.description = newDescription;
      this.onUpdateApartment();
    },
    onUpdateApartment() {
      this.$emit("update-apartment", this.apartment);
    },
  },
  components: { CRichTextEditor },
  props: ["data"],
  mounted() {
    this.apartment = JSON.parse(JSON.stringify(this.data));
  },
};
</script>
<style  scoped>
</style>

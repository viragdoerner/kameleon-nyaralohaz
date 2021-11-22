<template>
  <v-container class="d-flex px-16 col-10" fluid>
    <v-card width="50%" class="pa-10 ma-10 transparentCard" elevation="0">
      <v-card-title class="text-h3 zabatana cgreen--text">{{
        apartment.name
      }}</v-card-title>
      <v-card-text>
        <v-row class="pb-2">
          <v-text-field
            color="cgreen"
            label="Apartman neve"
            :rules="[$store.state.rules.required]"
            v-model="apartment.name"
            v-on:change="onUpdateApartment"
            maxlength="50"
          ></v-text-field>
        </v-row>
        <v-row class="pb-2">
          <v-text-field
            color="cgreen"
            label="Ár Ft/éj"
            :rules="[
              $store.state.rules.required,
              $store.state.rules.pricerange,
            ]"
            v-model="apartment.price"
            v-on:change="onUpdateApartment"
            type="number"
          ></v-text-field>
        </v-row>
        <c-rich-text-editor class="pt-5"
          :description="apartment.description"
          v-on:update-description="onUpdateDescription"
        ></c-rich-text-editor>
      </v-card-text>
    </v-card>
    <v-card width="50%" class="pa-10 ma-10">
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
    }
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

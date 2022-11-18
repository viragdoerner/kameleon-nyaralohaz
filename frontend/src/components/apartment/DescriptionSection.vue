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
    <reserve-section-date-picker :apartments="[data]"></reserve-section-date-picker>
  </v-container>
</template>

<script>
import CRichTextEditor from "../home/RichTextEditor.vue";
import ReserveSectionDatePicker from "../home/ReserveSectionDatePicker.vue"
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
  components: { CRichTextEditor, ReserveSectionDatePicker },
  props: ["data"],
  mounted() {
    this.apartment = JSON.parse(JSON.stringify(this.data));
  },
};
</script>
<style  scoped>
</style>

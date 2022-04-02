<template>
  <v-card class="mb-12" elevation="0">
    <v-row class="d-flex justify-space-between align-center">
      <v-col class="col-4 d-flex flex-column">
        <v-combobox
          v-model="selected_apartment"
          :items="items"
          label="Apartman"
          solo
          color="cgreen"
        ></v-combobox>
        <v-checkbox
          v-model="pet_checkbox"
          label="Hozzuk házi kedvencünket is"
          hint="Plusz költséggel nem jár, de korlátozhatja az elérhető dátumok számát."
          persistent-hint
          color="cgreen"
        ></v-checkbox>
        <v-text-field
          :value="'Fizetendő összeg: '+ price"
          label="Solo"
          solo dense
          readonly
          class="mt-15"
          :hint="'Az ' + selected_apartment + 'költsége egy éjszakára '+ price+' Ft'"
          persistent-hint
        ></v-text-field>
      </v-col>
      <v-col class="d-flex justify-start col-6">
        <v-date-picker v-model="dates" range color="cgreen"></v-date-picker>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
export default {
  name: "CBookingStepOne",
  props: [],
  components: {},
  data: () => ({
    selected_apartment: "",
    items: ["Programming", "Design", "Vue", "Vuetify"],
    pet_checkbox: true,
    dates: ["2019-09-10", "2019-09-20"],
    price: 0
  }),
  methods: {},
  computed: {
    dateRangeText() {
      return this.dates.join(" ~ ");
    },
  },
  mounted(){
    
  }
};
</script>

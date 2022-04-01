

<template>
  <v-card class="mb-12" elevation="0">
    <v-row class="d-flex justify-space-between">
      <v-col class="col-5">
        <v-combobox
          v-model="select"
          :items="items"
          label="Apartman"
          solo
        ></v-combobox>
        <v-checkbox
          v-model="checkbox"
          label="Hozzuk házi kedvencünket is"
          hint="Plusz költséggel nem jár, de korlátozhatja az elérhető dátumok számát."
          persistent-hint
        ></v-checkbox>
      </v-col>
      <v-col >
        <v-date-picker v-model="dates" range></v-date-picker>
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
    select: "",
    items: ["Programming", "Design", "Vue", "Vuetify"],
    checkbox: true,
    dates: ["2019-09-10", "2019-09-20"],
  }),
  methods: {},
  computed: {
    dateRangeText() {
      return this.dates.join(" ~ ");
    },
  },
};
</script>

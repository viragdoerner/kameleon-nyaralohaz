<template>
  <div>
    <v-divider class="my-5" v-if="!$vuetify.breakpoint.mobile"></v-divider>
    <v-text-field
      :value="totalCost + ' Ft'"
      label="Fizetendő összeg"
      outlined
      color="cgreen"
      dense
      readonly
      class="mt-sm-7 mt-0 pt-0"
      :hint="
        'Az ' +
        selectedApartment.name +
        ' költsége egy éjszakára ' +
        selectedApartment.price +
        ' Ft'
      "
      :persistent-hint="!!selectedApartment"
    ></v-text-field>
    <v-expand-transition>
      <div class="pt-7" v-if="dates.length > 0" caption>
        Érkezés napja: {{ arrival }}
      </div>
    </v-expand-transition>
    <v-expand-transition>
      <div v-if="dates.length == 2" caption>Távozás napja: {{ departure }}</div>
    </v-expand-transition>
  </div>
</template>

<script>
export default {
  name: "BookingStepOneInfo",
  props: ["totalCost", "selectedApartment", "dates", "arrival", "departure"],
  components: {},
  data: () => ({}),
  methods: {},
  computed: {},
  mounted() {},
};
</script>

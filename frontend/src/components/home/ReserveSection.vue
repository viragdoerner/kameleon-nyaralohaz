<template>
  <v-container class="d-flex px-16 col-10" fluid>
    <v-card width="50%" class="pa-10 ma-10">
      <v-card-title class="text-h3 zabatana cgreen--text" >Foglalj apartmant</v-card-title>
      <v-card-text class="roboto font-weight-light pb-2">
        {{description}}
      </v-card-text>
      <v-card-text class="roboto font-weight-regular">
        Idén nyáron pedig várunk Titeket is sok szeretettel a Kaméleon
        Nyaralóházban, csak 2 perc sétára a strandtól!
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
          <v-btn elevation="2" x-large color="corange" class="white--text">FOGLALÁS </v-btn>
        </v-row>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: "CReserveSection",
data: () => ({
    dateRangeText: null
  }),
  components: {},
  props: ["description"],
};
</script>

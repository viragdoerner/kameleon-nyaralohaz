<template>
  <v-container class="d-flex  px-16" fluid>
    <v-card width="80%" class="pa-10 ma-10">
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
      </v-card-text>
    </v-card>
    <v-card width="100%" class="pa-10 ma-10">
      <v-card-title>Foglalj apartmant</v-card-title>
      <v-card-text>
        Reggeli madárfütty-koncert, hűs falak, árnyékos teraszok, hosszú
        pancsolások és esti sütögetések Mi ezek miatt szeretünk annyira a
        balatonszemesi nyaralónkban lenni.
      </v-card-text>
      <v-card-text>
        Idén nyáron pedig várunk Titeket is sok szeretettel a Kaméleon
        Nyaralóházban, csak 2 perc sétára a strandtól!
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: "CReserveSection",

  components: {},
};
</script>

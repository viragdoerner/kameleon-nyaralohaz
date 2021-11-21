<template>
  <v-snackbar v-model="$store.state.snackbar" :timeout="timeout" color="cgreen" text>
    {{ successMsg }}

    <template v-slot:action="{ attrs }">
      <v-btn color="cgreen" text v-bind="attrs" @click="snackbar = false">
        Close
      </v-btn>
    </template>
  </v-snackbar>
</template>

<script>
export default {
  name: "CSnackBar",
  data: () => ({
    successMsg: "Sikeres mentés",
    timeout: 2000,
  }),
  computed: {},
  mounted() {},
  methods: {},
};
</script>

<style>
</style>

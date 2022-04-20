<template>
  <v-dialog v-model="confirmDialog.isOpen" max-width="290">
    <v-card>
      <v-card-title> {{confirmDialog.text}} </v-card-title>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="cgreen darken-1" text @click="cancel()">
          Mégse
        </v-btn>
        <v-btn color="corange darken-1" text @click="confirm()">
          {{confirmDialog.confirmButton}}
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: "CConfirmDialog",
  props:["confirmDialog"],
  data: () => ({}),
  computed: {
    
  },
  mounted() {},
  methods: {
      confirm(){
        this.confirmDialog.isOpen = false;
        this.$emit("confirm" );
      },
      cancel(){
        this.confirmDialog.isOpen = false;
        this.$emit("cancel" );
      }
  },
};
</script>

<style>
</style>

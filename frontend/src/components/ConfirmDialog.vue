<template>
  <v-dialog
    v-model="confirmDialog.isOpen"
    :max-width="confirmDialog.commentForm ? 600 : 290"
  >
    <v-card>
      <v-card-title> {{ confirmDialog.title }} </v-card-title>
      <v-card-text v-if="confirmDialog.commentForm || confirmDialog.text">
        <p>{{ confirmDialog.text }}</p>
        <v-textarea
          v-if="confirmDialog.commentForm"
          v-model="comment"
          :label="confirmDialog.commentForm.textfieldLabel"
          solo
          required
        ></v-textarea>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="cgreen darken-1" text @click="cancel()"> Mégse </v-btn>
        <v-btn
          color="corange darken-1"
          text
          @click="confirm()"
          :disabled="confirmDialog.commentForm && !this.comment"
        >
          {{ confirmDialog.confirmButton }}
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: "CConfirmDialog",
  props: ["confirmDialog"],
  data: () => ({
    comment: "",
  }),
  computed: {},
  mounted() {},
  methods: {
    confirm() {
      
      this.confirmDialog.isOpen = false;
      if (this.confirmDialog.commentForm) {
        console.log(this.comment);
        this.$emit("confirm", this.comment);
        this.comment="";
      } else {
        this.$emit("confirm");
      }
    },
    cancel() {
      this.confirmDialog.isOpen = false;
      this.$emit("cancel");
    },
  },
};
</script>

<style>
</style>

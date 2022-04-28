<template>
  <v-dialog
    v-model="dialog.isOpen"
    :max-width="dialog.commentForm ? 600 : 290"
    :retain-focus="false"
  >
    <v-card>
      <v-card-title> {{ dialog.title }} </v-card-title>
      <v-card-text v-if="dialog.commentForm || dialog.text">
        <p>{{ dialog.text }}</p>
        <v-combobox
          v-if="
            dialog.commentForm && dialog.commentForm.dropdownLabel
          "
          class="my-0"
          v-model="form.newStatus"
          :items="dialog.commentForm.dropdownItems"
          :label="dialog.commentForm.dropdownLabel"
          solo
          item-text="name"
          color="cgreen"
        ></v-combobox>
        <v-textarea
          v-if="dialog.commentForm"
          v-model="form.comment"
          :label="dialog.commentForm.textfieldLabel"
          solo
        ></v-textarea>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="cgreen darken-1" text @click="cancel()"> Mégse </v-btn>
        <v-btn
          :color="dialog.confirmButtonColor"
          text
          @click="confirm()"
          :disabled="isButtonDisabled"
        >
          {{ dialog.confirmButton }}
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "CConfirmDialog",
  props: [],
  data: () => ({
    form: {
      comment: "",
      newStatus: "",
    },
  }),
  computed: {
    computed: {
      ...mapState(["dialog"]),
    },
    isButtonDisabled() {
      return (
        (!!this.dialog.commentForm &&
          this.dialog.commentForm.textfieldRequired &&
          !this.form.comment) ||
        (!!this.dialog.commentForm &&
          this.dialog.commentForm.dropdownItems.length > 0 &&
          !this.form.newStatus)
      );
    },
  },
  mounted() { console.log(this.dialog)},
  methods: {
    confirm() {
      this.dialog.isOpen = false;
      if (!!this.dialog.commentForm) {
        this.form.newStatus = this.form.newStatus.status;
        this.$emit("confirm", this.form);
      } else {
        this.$emit("confirm");
      }
      this.form.comment = "";
      this.form.newStatus = "";
    },
    cancel() {
      this.form.comment = "";
      this.form.newStatus = "";
      this.dialog.isOpen = false;
      this.$emit("cancel");
    },
  },
};
</script>

<style>
</style>

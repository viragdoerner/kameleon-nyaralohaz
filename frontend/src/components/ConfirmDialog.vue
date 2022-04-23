<template>
  <v-dialog
    v-model="confirmDialog.isOpen"
    :max-width="confirmDialog.commentForm ? 600 : 290"
  >
    <v-card>
      <v-card-title> {{ confirmDialog.title }} </v-card-title>
      <v-card-text v-if="confirmDialog.commentForm || confirmDialog.text">
        <p>{{ confirmDialog.text }}</p>
        <v-combobox
          v-if="
            confirmDialog.commentForm && confirmDialog.commentForm.dropdownLabel
          "
          class="my-0"
          v-model="form.newStatus"
          :items="confirmDialog.commentForm.dropdownItems"
          :label="confirmDialog.commentForm.dropdownLabel"
          solo
          color="cgreen"
        ></v-combobox>
        <v-textarea
          v-if="confirmDialog.commentForm"
          v-model="form.comment"
          :label="confirmDialog.commentForm.textfieldLabel"
          solo
        ></v-textarea>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="cgreen darken-1" text @click="cancel()"> Mégse </v-btn>
        <v-btn
          :color="confirmDialog.confirmButtonColor"
          text
          @click="confirm()"
          :disabled="isButtonDisabled"
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
    form: {
      comment: "",
      newStatus: "",
    },
    //   isOpen: false,
    //   title: "",
    //   text: "",
    //   confirmButton: "OK",
    //   confirmButtonColor: "success",
    //   commentForm: {
    //     textfieldLabel: "",
    //     textfieldRequired: true,
    //     dropdownLabel: "",
    //     dropdownItems: [],
    //   }
  }),
  computed: {
    isButtonDisabled() {
      return (
        (this.confirmDialog.commentForm && this.confirmDialog.commentForm.textfieldRequired &&
          !this.form.comment) ||
        (this.confirmDialog.commentForm && this.commentForm.dropdownLabel && this.commentForm.dropdownItems.length > 0 && !this.form.newStatus)
      );
    },
  },
  mounted() {},
  methods: {
    confirm() {
      this.confirmDialog.isOpen = false;
      if (this.confirmDialog.commentForm) {
        console.log(this.form);
        this.$emit("confirm", this.form);
        this.form.comment = "";
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

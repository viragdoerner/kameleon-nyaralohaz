<template>
  <v-dialog
    v-model="confirmDialog.isOpen"
    :max-width="confirmDialog.commentForm ? 600 : 290"
    :retain-focus="false"
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
          item-text="name"
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
  }),
  computed: {
    isButtonDisabled() {
      return (
        (!!this.confirmDialog.commentForm &&
          this.confirmDialog.commentForm.textfieldRequired &&
          !this.form.comment) ||
        (!!this.confirmDialog.commentForm &&
          this.confirmDialog.commentForm.dropdownItems.length > 0 &&
          !this.form.newStatus)
      );
    },
  },
  mounted() {},
  methods: {
    confirm() {
      this.confirmDialog.isOpen = false;
      if (!!this.confirmDialog.commentForm) {
        this.form.newStatus = this.form.newStatus.status;
        console.log(this.form);
        this.$emit("confirm", this.form);
      } else {
        this.$emit("confirm");
      }
      
    },
    cancel() {
      this.form.comment = "";
      this.form.newStatus = "";
      this.confirmDialog.isOpen = false;
      this.$emit("cancel");
    },
  },
};
</script>

<style>
</style>

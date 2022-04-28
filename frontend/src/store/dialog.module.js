export const dialog = {
    namespaced: true,
    state: {
        dialogData: {
            isOpen: false,
            title: "",
            text: "",
            confirmButton: "OK",
            confirmButtonColor: "success",
            hasForm: false,
            form: {
                textfieldLabel: "",
                textfieldRequired: true,
                dropdownLabel: "",
                dropdownItems: [],
            },
        },
        mutations: {
            closeDialog(state) {
                state.dialogData.isOpen = false;
            },
            openSimpleDialog(state, payload) {
                state.dialogData.title = payload.title || "";
                state.dialogData.text = payload.text || "";
                state.dialogData.confirmButtonColor = payload.confirmButtonColor || "success";
                state.dialogData.confirmButton = payload.confirmButton || "OK";
                state.dialogData.hasForm = false;
                state.dialogData.confirmButton = payload.confirmButton;
                state.dialogData.isOpen = true;
            },
            openDialogWithComment(state, payload) {
                this.openSimpleDialog(state, payload);
                state.dialogData.hasForm = true;
                state.dialogData.form.textfieldLabel = payload.textfieldLabel || "";
                state.dialogData.form.textfieldRequired = payload.textfieldRequired || "";
                state.dialogData.form.dropdownLabel = payload.dropdownLabel || "";
                state.dialogData.form.dropdownItems = payload.dropdownItems || "";
                state.dialogData.isOpen = true;
            },
        }
    }
};
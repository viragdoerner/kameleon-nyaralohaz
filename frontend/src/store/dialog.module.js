const formDialog = {
    isOpen: false,
    title: "",
    text: "",
    confirmButton: "OK",
    confirmButtonColor: "success",
    form: {
        textfieldLabel: "",
        textfieldRequired: true,
        dropdownLabel: "",
        dropdownItems: [],
    },
}
const simpleDialog = {
    isOpen: false,
    title: "",
    text: "",
    confirmButton: "OK",
    confirmButtonColor: "success",
    form: null
}

export const dialog = {
    namespaced: true,
    state: {
        dialogData: {
            isOpen: false,
            title: "",
            text: "",
            confirmButton: "OK",
            confirmButtonColor: "success",
            form: {
                textfieldLabel: "",
                textfieldRequired: true,
                dropdownLabel: "",
                dropdownItems: [],
            },
        },
    },
    mutations: {
        closeDialog(state) {
            console.log('hey');
            state.dialogData.isOpen = false;
        },
        openSimpleDialog(state, payload) {
            state.dialogData = simpleDialog;
            state.dialogData = Object.assign({}, payload);
            state.dialogData.isOpen = true;
        },
        openDialogWithForm(state, payload) {
            state.dialogData = formDialog;
            state.dialogData = Object.assign({}, payload);
            state.dialogData.isOpen = true;
        }
    }
};
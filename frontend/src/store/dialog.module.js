const confirmDialogOriginal = {
    isOpen: false,
    title: "",
    text: "",
    confirmButton: "OK",
    confirmButtonColor: "success",
    commentForm: {
        textfieldLabel: "",
        textfieldRequired: true,
        dropdownLabel: "",
        dropdownItems: [],
    },
}

export const dialog = {
    namespaced: true,
    state: {
        dialog: {
            isOpen: false,
            title: "",
            text: "",
            confirmButton: "OK",
            confirmButtonColor: "success",
            commentForm: {
                textfieldLabel: "",
                textfieldRequired: true,
                dropdownLabel: "",
                dropdownItems: [],
            },
        }
    },
    mutations: {
        openSimpleDialog(state, payload) {
            state.dialog = payload;
        },
        openDialogWithComment(state, payload) {

        },
        openDialogWithCommentAndDropdown(state, payload) {

        }
    }
};
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
        dialogData: {
            isOpen: false,
            title: "",
            text: "",
            confirmButton: "OK",
            confirmButtonColor: "success",
            commentForm: null,
        },
        commentForm: {
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
            state.dialogData.title = payload.title;
            state.dialogData.confirmButton = payload.confirmButton;
            state.dialogData.isOpen = true;
        },
        openDialogWithComment(state, payload) {

        },
        openDialogWithCommentAndDropdown(state, payload) {

        }
    }
};
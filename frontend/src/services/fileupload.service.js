import ApiService from "./api.service"

class FileuploadService {


    uploadFileURLsToBackend(urls, apartmentId) {
        const formData = new FormData();
        for (let url of urls) {
            formData.append("imageURLs", url);
        }
        formData.append("apartmentId", apartmentId);
        ApiService.POST("apartment/addpictures", formData)
            .then((response) => {
                this.$emit("uploaded-pictures", response.data);
                this.$store.commit("showMessage", {
                    active: true,
                    color: "cgreen",
                    message: "Sikeres feltöltés",
                });
            })
            .catch((error) => {
                this.$store.commit("showMessage", {
                    active: true,
                    color: "error",
                    message: "Nem sikerült feltölteni a képeket. Próbáld újra!",
                });
            });
    }
    saveImages(files, apartmentId) {
        console.log(files.length + " files to upload")
        var fileResponses = [];
        if (files.length > 0) {
            files.forEach((file, index, array) => {
                console.log("upload image " + index)
                this.uploadFileToCloudinary(file).then((fileResponse) => {
                    console.log("finished uploading image " + index)
                    fileResponses.push(fileResponse);
                    if (index == array.length - 1) {
                        console.log("finished uploading files to cloudinary")
                        var urls = fileResponses.map((fileResponse) => fileResponse.url);
                        console.log(urls)
                        this.uploadFileURLsToBackend(urls, apartmentId)
                    }

                });
            });
        }

    }
    uploadFileToCloudinary(file) {
        return new Promise(function (resolve, reject) {
            //Ideally these to lines would be in a .env file
            const CLOUDINARY_URL =
                "https://api.cloudinary.com/v1_1/dtqjqi3ii/upload";
            const CLOUDINARY_UPLOAD_PRESET = "hsdguvou";

            let formData = new FormData();
            formData.append("upload_preset", CLOUDINARY_UPLOAD_PRESET);
            formData.append("folder", "kameleonbalaton");
            formData.append("file", file);

            let request = new XMLHttpRequest();
            request.open("POST", CLOUDINARY_URL, true);
            request.setRequestHeader("X-Requested-With", "XMLHttpRequest");

            request.onreadystatechange = () => {
                // File uploaded successfully
                if (request.readyState === 4 && request.status === 200) {
                    console.log('one file uploaded')

                    let response = JSON.parse(request.responseText);
                    resolve(response);
                }

                // Not succesfull, let find our what happened
                if (request.status !== 200) {
                    let response = JSON.parse(request.responseText);
                    let error = response.error.message;
                    alert("error, status code not 200 " + error);
                    reject(error);
                }
            };

            request.onerror = (err) => {
                alert("error: " + err);
                reject(err);
            };

            request.send(formData);
        });
    }

}
export default new FileuploadService();
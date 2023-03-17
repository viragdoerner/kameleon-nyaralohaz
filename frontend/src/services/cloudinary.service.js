import ApiService from "./api.service"

class CloudinaryService {

    saveImages(files, apartmentId) {
        if (files.length <= 0) return;
        var that = this;
        return new Promise(function (resolve, reject) {
            that.getCoudinaryCredentials()
                .then(function (response) {
                    return response;
                })
                .then(function (credentials) {
                    const requests = files.map(file => {
                        return that.uploadFileToCloudinary(file, credentials[0], credentials[1]);
                    });
                    return Promise.all(requests);
                })
                .then(function (cloudinaryResponses) {
                    var urls = cloudinaryResponses.map((fileResponse) => fileResponse.url);
                    return urls;
                })
                .then(function (urls) {
                    resolve(that.uploadFileURLsToBackend(urls, apartmentId))
                })
                .catch((error) => {
                    reject(error);
                });
        });
    }

    async uploadFileURLsToBackend(urls, apartmentId) {
        const formData = new FormData();
        for (let url of urls) {
            formData.append("imageURLs", url);
        }
        formData.append("apartmentId", apartmentId);
        return ApiService.POST("apartment/addpictures", formData);
    }

    async getCoudinaryCredentials() {
        var response = await ApiService.GET("user/cloudinary")
        return response.data;
    }
    uploadFileToCloudinary(file, CLOUD_NAME, CLOUDINARY_UPLOAD_PRESET) {
        return new Promise(function (resolve, reject) {
            //Ideally these two lines would be in a .env file
            const CLOUDINARY_URL =
                "https://api.cloudinary.com/v1_1/" + CLOUD_NAME + "/upload";

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

                    let response = JSON.parse(request.responseText);
                    resolve(response);
                }

                // Not succesfull, let find our what happened
                if (request.status !== 200) {
                    let response = JSON.parse(request.responseText);
                    let error = response.error.message;
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
export default new CloudinaryService();
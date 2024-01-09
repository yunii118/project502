var commonLib = commonLib || {};
/**
* 1. 파일 업로드
*/
commonLib.fileManager = {
    /**
    * 파일 업로드 처리
    *
    * @Param files : 업로드 파일 정보 목록
    * @Param location : 파일 그룹(gid) 안에서 위치 구분값(예 - 메인이미지, 목록이미지, 상세페이지 이미지 등)
    * @Param imageOnly : true - 이미지만 업로드 가능하게 통제
    * @Param singleFile : true - 단일 파일 업로드(기존 파일 삭제)
    */
    upload(files, location, imageOnly, singleFile){
        try{
            if(!files || files.length == 0){
                throw new Error("업로드할 파일을 선택하세요.");
            }


            // gid
            const gidEl = document.querySelector("[name='gid']");
            if(!gidEl || !gidEl.value.trim()){
                throw new Error("gid가 누락되었습니다.");
            }

            const gid = gidEl.value.trim();
            const formData = new FormData(); // 기본 Content-Type : multipart/form-data, 파일에 특화
            formData.append("gid", gid);

            if(location){
                formData.append("location", location);
            }

            if(singleFile){
             formData.append("singleFile", singleFile);
            }

            // 이미지만 업로드 가능일 때 처리
            if(imageOnly){
                for(const file of files){
                // 이미지 형식이 아닌 파일이 포함되어 있는 경우
                    if(file.type.indexOf("image/") == -1){
                        throw new Error("이미지 형식의 파일만 업로드 가능합니다.");
                    }
                }

                formData.append("imageOnly", imageOnly);
            }
            for(const file of files){
                formData.append("file", file);
            }

            const { ajaxLoad } = commonLib;
            ajaxLoad("POST", "/api/file", formData, "json")
                .then(res => {
                    if(res && res.success){
                        // 파일 업로드 성공
                        if(typeof parent.callbackFileUpload == 'function'){
                            parent.callbackFileUpload(res.data);
                        }
                    }else{
                        if(res){
                        // 파일 업로드 실패
                            alert(res.message);
                        }
                    }
                })
                .catch(err => console.error(err));


        }catch(err){
            alert(err.message);
            console.error(err);
        }
    }
};

// 이벤트 처리
window.addEventListener("DOMContentLoaded", function() {
    const uploadFiles = document.getElementsByClassName("upload_files");



    for (const el of uploadFiles) {
        el.addEventListener("click", function() {
            const fileEl = document.createElement("input");
            fileEl.type="file";
            fileEl.multiple = true; // 파일 여러개 선택 가능

            const imageOnly = this.dataset.imageOnly == 'true';
            fileEl.imageOnly = imageOnly;
            fileEl.location = this.dataset.location;

            const singleFile = this.dataset.singleFile == 'true';
            fileEl.singleFile = singleFile;
            if(singleFile) fileEl.multiple = false;
            // 파일 선택시 이벤트 처리
            fileEl.addEventListener("change", function(e){
                const imageOnly = fileEl.imageOnly || false;
                const location = fileEl.location;
                const singleFile = fileEl.singleFile;

                commonLib.fileManager.upload(e.target.files, location, imageOnly, singleFile);

            });
            fileEl.click();
        });
    }


});
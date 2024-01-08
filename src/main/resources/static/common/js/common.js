var commonLib = commonLib || {};

/**
* ajax 요청, 응답 편의 함수
*
* @Param method: 요청방식 - Get, Post, Put, Patch, Delete ...
* @Param url : 요청 URL
* @Param params : 요청 데이터(Post, Put, Patch ... )
* @Param responseType : json : javascript 객체로 변환
*/
commonLib.ajaxLoad = function(method, url, params, responseType){
    method = method || "GET";
    params = params || null;

    const token = document.querySelector("meta[name='_csrf']").content;
    const tokenHeader = document.querySelector("meta[name='_csrf_header']").content;

    return new Promise((resolve, reject) => {
        const xhr = new XMLHttpRequest();
        xhr.open(method, url);
        xhr.setRequestHeader(tokenHeader, token);

        xhr.send(params); // 요청 바디에 실릴 데이터, 키=값, FormData 객체 등

        xhr.onreadystatechange = function(){
            if(xhr.status == 200 && xhr.readyState == XMLHttpRequest.DONE){
                const resData = (responseType && responseType.toLowerCase() == 'json') ? JSON.parse(xhr.responseText) : xhr.responseText;

                resolve(resData); // 성공시 응답데이터
            }
        };
        xhr.onabort = function(err){
            reject(err); //중단 시
        };

        xhr.onerror = function(err){
            reject(err); // 요청 또는 응답시 오류 발생했을 때 실행
        };
    });

};
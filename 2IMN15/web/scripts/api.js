var api = {
    delete: function(url, id) {
        return api.serviceCall("DELETE", url + '?' + $.param(id));
    },
    get: function(url) {
        return api.serviceCall("GET", url, undefined);
    },
    post: function(url, data) {
        return api.serviceCall("POST", url, JSON.stringify(data));
    },
    serviceCall: function(type, url, data) {
        var drd = $.Deferred();

        $
            .ajax({
                type: type,
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                url: url,
                data: data
            })
            .done(function (result) {
                if (!result.successful) {
                    if (!result.message) {
                        drd.reject("The received data is malformed. Data: " + result);
                    } else {
                        drd.reject(result.message);
                    }
                } else {
                    drd.resolve(result.data);
                }
            })
            .fail(function (jqXHR, textStatus) {
                drd.reject("Request failed: " + textStatus);
            });

        return drd.promise();
    }
}
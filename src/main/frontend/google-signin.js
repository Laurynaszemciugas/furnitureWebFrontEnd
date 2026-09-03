window.initGoogleButton = function (element, clientId) {
    console.log("initGoogleButton called");

    function render() {
        console.log("Trying to render Google button");
        console.log("Google:", window.google);

        if (!window.google || !window.google.accounts) {
            console.log("Google GIS not loaded yet");
            setTimeout(render, 200);
            return;
        }

        google.accounts.id.initialize({
            client_id: clientId,
            callback: function (response) {
                element.dispatchEvent(
                    new CustomEvent("google-login", {
                        detail: response.credential
                    })
                );
            }
        });

        google.accounts.id.renderButton(element, {
            theme: "outline",
            size: "large",
            text: "continue_with",
            shape: "rectangular",
            width: 300
        });

        console.log("Google button rendered");
    }

    render();
};
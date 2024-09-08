let stompClient = null;
let headers = {
    Authorization: 'Authorization',
};

export function fetchInitialMessages() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/get_all', true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            var messages = JSON.parse(xhr.responseText);
            messages.forEach(function (message) {
                updateMessages({body: message});
            });
            connect();
            initialMessageSeparatingLine();
        } else {
            console.error('Failed to fetch initial messages');
        }
    };
    xhr.send();
}

export function sendMessage() {
    let messageElement = document.getElementById('message');
    let nameElement = document.getElementById('name');
    if (messageElement.value.trim() === "" || nameElement.value.trim() === "") {
        alert("Please make sure to fill in both the message and the name fields.");
        return;
    }
    stompClient.send("/app/produce", headers, nameElement.value + " : " + messageElement.value);
    messageElement.value = "";
}

function connect() {
    let socket = new SockJS('/ws/chat');
    stompClient = Stomp.over(socket);
    stompClient.debug = null;

    stompClient.connect(headers, function (frame) {
        stompClient.subscribe('/topic/topic', function (greeting) {
            updateMessages(greeting);
        });
    });
}

function updateMessages(greeting) {
    let messages = document.getElementById('messages');
    let nameElement = document.getElementById('name');
    let new_message_container = document.createElement('div');

    new_message_container.style.display = "flex";
    new_message_container.style.width = "100%";

    let new_message = document.createElement('div');
    new_message.style.padding = "3px 5px";
    new_message.style.margin = "0 0 10px 0";
    new_message.style.borderRadius = "3px";
    new_message.style.width = "fit-content";
    new_message.innerText = greeting.body;

    if (nameElement.value === greeting.body.toString().split(" : ")[0]){
        new_message_container.style.flexDirection = "row-reverse";

        new_message.style.backgroundColor = "white";
        new_message.style.border = "solid 1px black";
        new_message.style.color = "black";
    } else {
        new_message.style.backgroundColor = "#0090ff";
        new_message.style.color = "white";
    }

    new_message_container.appendChild(new_message);
    messages.appendChild(new_message_container);

    scrollToBottom();
}

function initialMessageSeparatingLine() {
    let messages = document.getElementById('messages');
    let new_line_container = document.createElement('div');

    new_line_container.style.display = "flex";
    new_line_container.style.width = "100%";

    let new_line = document.createElement('div');
    new_line.innerText = "earlier messages";
    new_line.style.display = "flex";
    new_line.style.alignContent = "center";
    new_line.style.justifyContent = "center";
    new_line.style.padding = "3px 5px";
    new_line.style.margin = "10px 0px";
    new_line.style.fontSize = "12px";
    new_line.style.width = "100%";
    new_line.style.height = "fit-content";
    new_line.style.backgroundColor = "black";

    new_line_container.appendChild(new_line);
    messages.appendChild(new_line_container);

    scrollToBottom();
}

function scrollToBottom() {
    const container = document.querySelector('.scroll-container');
    container.scrollTo(0, container.scrollHeight);
}

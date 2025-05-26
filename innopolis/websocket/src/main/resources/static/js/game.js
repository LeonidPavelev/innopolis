let stompClient = null;
let playerName = '';

function connectToGame() {
    playerName = document.getElementById('player-name').value.trim();

    if (!playerName) {
        alert("Пожалуйста, введите ваше имя!");
        return;
    }

    document.getElementById('player-name-display').textContent = playerName;
    document.getElementById('current-player-info').style.display = 'block';

    document.getElementById('name-form').style.display = 'none';
    document.getElementById('game-area').style.display = 'block';

    const socket = new SockJS('/ws-wordle-race');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {
        console.log("Connected: " + frame);

        stompClient.subscribe('/topic/gameState', function(message) {
            updateGameState(JSON.parse(message.body));
        });

        stompClient.send("/app/game.addPlayer", {},
            JSON.stringify({
                type: "JOIN",
                sender: playerName,
                content: ""
            })
        );
    }, function(error) {
        console.error("Connection error: ", error);
    });

    localStorage.setItem('playerName', playerName);
}

function sendGuess() {
    const guess = document.getElementById('guess-input').value.trim().toUpperCase();

    if (!guess) {
        alert("Пожалуйста, введите слово!");
        return;
    }

    if (guess.length !== 5) {
        alert("Слово должно содержать 5 букв!");
        return;
    }

    stompClient.send("/app/game.sendMessage", {},
        JSON.stringify({
            type: "GUESS",
            sender: playerName,
            content: guess
        })
    );

    document.getElementById('guess-input').value = "";
}

function updateGameState(state) {
    let html = `<h2>Состояние игры</h2>`;
    html += `<p>Игра ${state.gameOver ? 'завершена' : 'в процессе'}</p>`;
    html += `<h3>Игроки:</h3>`;

    state.players.forEach(player => {
        const isCurrentPlayer = player.name === playerName;
        html += `
                    <div class="player ${player.winner ? 'winner' : ''} ${isCurrentPlayer ? 'current-player' : ''}">
                        <strong>${player.name}</strong>
                        <p>Попытки: ${player.guesses.join(', ')}</p>
                        ${player.winner ? '<p>🏆 Победитель!</p>' : ''}
                        ${isCurrentPlayer ? '<p>(Это вы)</p>' : ''}
                    </div>
                `;
    });

    document.getElementById('game-state').innerHTML = html;
}

window.onload = function() {
    if (localStorage.getItem('playerName')) {
        document.getElementById('player-name').value = localStorage.getItem('playerName');
    }

    document.getElementById('guess-input').addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            sendGuess();
        }
    });
};
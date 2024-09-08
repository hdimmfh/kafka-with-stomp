export function auto_submit() {
    document.addEventListener('DOMContentLoaded', (event) => {
        const message = document.getElementById('message');
        let submitTimeout;

        // Detect Enter key press
        message.addEventListener('keydown', function(event) {
            if (event.key === 'Enter') {
                event.preventDefault();

                // Clear any existing timeout
                if (submitTimeout) clearTimeout(submitTimeout);

                // Set a timeout to handle the submit action
                submitTimeout = setTimeout(() => {
                    sendMessage();
                }, 10);
            }
        });
    });
}

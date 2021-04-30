var stompClient = null;
            var roomname;
            var bottom;
            
            function connect() {
            	bottom = document.getElementById('chatscreen');
            	scrollToBottom ();
            	roomname = "[[${#session.getAttribute('roomName')}]]";
                var socket = new SockJS('/chat/');
                stompClient = Stomp.over(socket);  
                stompClient.connect({}, function(frame) {
                    console.log('Connected: ' + frame);
                    stompClient.subscribe('/topic/messages/'+roomname, function(messageOutput) {
                        showMessageOutput(JSON.parse(messageOutput.body));
                    });
                });
                
            }
            
            function disconnect() {
                if(stompClient != null) {
                    stompClient.disconnect();
                }
                setConnected(false);
                console.log("Disconnected");
            }
            
            function sendMessage(e) {
            	 
                var from = document.getElementById('from').value;
                var text = document.getElementById('text').value;
                stompClient.send("/app/chat/"+roomname, {}, 
                  JSON.stringify({'sender':from, 'content':text}));
                 document.getElementById('text').value='';
                 
            }
            
            function showMessageOutput(messageOutput) {
                var response = document.getElementById('response');
                var p = document.createElement('p');
                p.style.wordWrap = 'break-word';
                p.appendChild(document.createTextNode(messageOutput.sender + ": " 
                  + messageOutput.text + " (" + messageOutput.time + ")"));
                response.appendChild(p);
                scrollToBottom ();
            }
            $(function () {
           	
			    $("#myForm").on('submit', function (e) {
			        e.preventDefault();
			    });
			    $( "#sendMessage" ).click(function() { sendMessage(); });
			});
        
		
			function scrollToBottom () {
				var scrollingElement = (bottom || document.body);
			   scrollingElement.scrollTop = scrollingElement.scrollHeight;
			}
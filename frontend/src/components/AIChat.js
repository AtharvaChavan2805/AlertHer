import React, { useState, useRef, useEffect } from 'react';
import './AIChat.css';

function AIChat({ userId, onRiskDetected }) {
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const messagesEndRef = useRef(null);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  const handleSendMessage = async (e) => {
    e.preventDefault();
    if (!input.trim()) return;

    // Add user message
    const userMessage = { type: 'user', text: input, timestamp: new Date() };
    setMessages([...messages, userMessage]);
    setInput('');
    setIsLoading(true);

    try {
      const response = await fetch(
        `http://localhost:8080/api/ai/analyze?userId=${userId}&message=${encodeURIComponent(input)}`,
        { method: 'POST' }
      );
      const data = await response.json();
      
      // Add AI response
      const aiMessage = {
        type: 'ai',
        text: data.data.message,
        riskLevel: data.data.riskLevel,
        timestamp: new Date()
      };
      setMessages(prev => [...prev, aiMessage]);

      // Notify parent if HIGH risk
      if (data.data.riskLevel === 'HIGH') {
        onRiskDetected({
          message: data.data.message,
          riskLevel: 'HIGH'
        });
      }
    } catch (error) {
      console.error('Error analyzing message:', error);
      const errorMessage = {
        type: 'error',
        text: 'Failed to analyze message. Please try again.',
        timestamp: new Date()
      };
      setMessages(prev => [...prev, errorMessage]);
    }

    setIsLoading(false);
  };

  return (
    <div className="ai-chat">
      <div className="chat-header">
        <h3>🤖 Safety Assistant</h3>
        <p>Share your concerns and get immediate assessment</p>
      </div>

      <div className="chat-messages">
        {messages.length === 0 ? (
          <div className="chat-empty">
            <p>👋 Hello! I'm your AI Safety Assistant.</p>
            <p>If you feel unsafe or distressed, please describe your situation.</p>
            <p>I'll assess the risk level and help you immediately.</p>
          </div>
        ) : (
          <>
            {messages.map((msg, idx) => (
              <div key={idx} className={`message message-${msg.type}`}>
                <div className="message-content">
                  <span className={`risk-badge risk-${msg.riskLevel || 'info'}`}>
                    {msg.riskLevel}
                  </span>
                  <p>{msg.text}</p>
                  <small>{new Date(msg.timestamp).toLocaleTimeString()}</small>
                </div>
              </div>
            ))}
            <div ref={messagesEndRef} />
          </>
        )}
      </div>

      <form onSubmit={handleSendMessage} className="chat-form">
        <input
          type="text"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          placeholder="Describe your situation..."
          disabled={isLoading}
          className="chat-input"
        />
        <button type="submit" disabled={isLoading || !input.trim()} className="chat-send">
          {isLoading ? '⏳' : '📤'}
        </button>
      </form>
    </div>
  );
}

export default AIChat;

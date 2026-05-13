import React, { useState } from 'react';
import { User, Lock, Eye, Mail } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import AuthLayout from '../layouts/AuthLayout';
import api from '../api/axios';

const FullRegistration = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (password !== confirmPassword) {
      alert("Passwords don't match!");
      return;
    }

    try {
      const response = await api.post("/api/auth/complete", {
        email,
        username,
        password
      });
      console.log(response.data);
      alert('Registration Complete!');
      navigate('/');
    } catch (error) {
      console.log(error);
    }
  };
  return (
    <AuthLayout
      title="Complete Profile"
      subtitle="Set up your username and password to finalize registration."
    >
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label className="form-label">Email</label>
          <div className="input-container">
            <input
              type="email"
              placeholder="Enter your email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
            <Mail size={18} className="input-icon" />
          </div>
        </div>

        <div className="form-group">
          <label className="form-label">Username</label>
          <div className="input-container">
            <input
              type="text"
              placeholder="Choose a username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
            <User size={18} className="input-icon" />
          </div>
        </div>

        <div className="form-group">
          <label className="form-label">Password</label>
          <div className="input-container">
            <input
              type="password"
              placeholder="••••••••"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
            <Lock size={18} className="input-icon" />
          </div>
        </div>

        <div className="form-group">
          <label className="form-label">Confirm Password</label>
          <div className="input-container">
            <input
              type="password"
              placeholder="••••••••"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              required
            />
            <Eye size={18} className="input-icon" style={{ cursor: 'pointer', pointerEvents: 'auto' }} />
          </div>
        </div>

        <button type="submit" className="btn-primary">Complete Registration</button>
      </form>
    </AuthLayout>
  );
};

export default FullRegistration;

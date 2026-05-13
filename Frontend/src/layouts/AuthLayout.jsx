import React from 'react';
import { Shield } from 'lucide-react';
import './AuthLayout.css';

const AuthLayout = ({ children, title, subtitle }) => {
  return (
    <div className="auth-layout">
      <div className="auth-header animate-fade-in">
        <div className="logo-container">
          <Shield size={24} color="#fff" />
        </div>
        <div className="product-label">PRODUCT</div>
        <div className="brand-name">BREAKING BIAS</div>
      </div>
      
      <div className="auth-title animate-fade-in" style={{ animationDelay: '0.1s' }}>
        <h1>{title}</h1>
        <p>{subtitle}</p>
      </div>

      <div className="auth-card animate-fade-in" style={{ animationDelay: '0.2s' }}>
        {children}
      </div>
      
      <div className="auth-footer">
        <div className="footer-left">
          <span className="dot"></span>
          INTELLIGENT SYSTEMS
        </div>
        <div className="footer-links">
          <a href="#">Privacy</a>
          <a href="#">Terms</a>
          <a href="#">Support</a>
        </div>
        <div className="footer-right">
          © 2024. SYSTEMS ARCHIVE
        </div>
      </div>
    </div>
  );
};

export default AuthLayout;

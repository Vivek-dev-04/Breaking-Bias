import React, { useState, useEffect } from 'react';
import { User, Eye, EyeOff } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import AuthLayout from '../layouts/AuthLayout';
import api from '../api/axios';

const Login = () => {

  const navigate = useNavigate();

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState('');

  // Clear old auth
  useEffect(() => {

    localStorage.removeItem('token');

  }, []);

  const handleLogin = async (e) => {

    e.preventDefault();

    setError('');

    try {

      // JWT LOGIN REQUEST
      const response = await api.post(

        '/api/auth/login',

        {
          username,
          password
        }

      );

      // STORE JWT TOKEN
      localStorage.setItem(
        'token',
        response.data
      );

      console.log(
        'JWT TOKEN:',
        response.data
      );

      navigate('/dashboard');

    } catch (err) {

      console.error(err);

      if (err.response?.status === 401) {

        setError(
          'Invalid username or password'
        );

      } else {

        setError(
          'Something went wrong. Try again.'
        );
      }
    }
  };

  return (

    <AuthLayout
      title="Welcome Back"
      subtitle="Please enter your credentials to access your secure workspace."
    >

      <form onSubmit={handleLogin}>

        {error && (

          <div
            style={{
              color: 'red',
              marginBottom: '10px',
              fontSize: '14px'
            }}
          >
            {error}
          </div>
        )}

        <div className="form-group">

          <label className="form-label">
            Username
          </label>

          <div className="input-container">

            <input
              type="text"
              placeholder="Enter your username"
              value={username}
              onChange={(e) =>
                setUsername(e.target.value)
              }
              required
            />

            <User
              size={18}
              className="input-icon"
            />

          </div>

        </div>

        <div className="form-group">

          <div className="form-label">

            <span>Password</span>

            <a
              href="#"
              onClick={(e) => {

                e.preventDefault();

                navigate(
                  '/forgot-password/request'
                );
              }}
            >
              Forgot Password?
            </a>

          </div>

          <div className="input-container">

            <input
              type={
                showPassword
                  ? 'text'
                  : 'password'
              }
              placeholder="••••••••"
              value={password}
              onChange={(e) =>
                setPassword(e.target.value)
              }
              required
            />

            <span
              onClick={() =>
                setShowPassword(!showPassword)
              }
            >

              {showPassword

                ? <EyeOff
                  size={18}
                  className="input-icon"
                  style={{ cursor: 'pointer' }}
                />

                : <Eye
                  size={18}
                  className="input-icon"
                  style={{ cursor: 'pointer' }}
                />
              }

            </span>

          </div>

        </div>

        <button
          type="submit"
          className="btn-primary"
        >
          Sign In
        </button>

        <div className="divider">
          <span>OR</span>
        </div>

        <button
          type="button"
          className="btn-secondary"
          onClick={() =>
            navigate('/register')
          }
        >
          Create Account
        </button>

      </form>

    </AuthLayout>
  );
};

export default Login;
import React from 'react';

import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate
} from 'react-router-dom';

import Login from './pages/Login';
import EmailRegistration from './pages/EmailRegistration';
import FullRegistration from './pages/FullRegistration';
import Dashboard from './pages/Dashboard';
import ForgotPassword from './pages/ForgotPassword';
import ResetPassword from './pages/ResetPassword';

// JWT Protected Route
const ProtectedRoute = ({ children }) => {

  const token =
    localStorage.getItem('token');

  if (!token) {

    return <Navigate to="/" replace />;
  }

  return children;
};

function App() {

  return (

    <Router>

      <Routes>

        <Route
          path="/"
          element={<Login />}
        />

        <Route
          path="/register"
          element={<EmailRegistration />}
        />

        <Route
          path="/register/complete"
          element={<FullRegistration />}
        />

        <Route
          path="/forgot-password/request"
          element={<ForgotPassword />}
        />

        <Route
          path="/forgot-password"
          element={<ResetPassword />}
        />

        <Route
          path="/dashboard"

          element={
            <ProtectedRoute>

              <Dashboard />

            </ProtectedRoute>
          }
        />

        <Route
          path="*"
          element={<Navigate to="/" />}
        />

      </Routes>

    </Router>
  );
}

export default App;
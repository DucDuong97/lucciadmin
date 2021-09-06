import 'react-toastify/dist/ReactToastify.css';
import './app.scss';

import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Card } from 'reactstrap';
import { BrowserRouter as Router } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import { hot } from 'react-hot-loader';

import { IRootState } from 'app/shared/reducers';
import { getSession } from 'app/shared/reducers/authentication';
import { getProfile } from 'app/shared/reducers/application-profile';
import { setLocale } from 'app/shared/reducers/locale';
import Header from 'app/shared/layout/header/header';
import Footer from 'app/shared/layout/footer/footer';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import ErrorBoundary from 'app/shared/error/error-boundary';
import { AUTHORITIES } from 'app/config/constants';
import AppRoutes from 'app/routes';

const baseHref = document.querySelector('base').getAttribute('href').replace(/\/$/, '');

export interface IAppProps extends StateProps, DispatchProps {}

export const App = (props: IAppProps) => {
  useEffect(() => {
    props.getSession();
    props.getProfile();
  }, []);

  const paddingTop = '60px';
  return (
    <Router basename={baseHref}>
      <div className="app-container" style={{ paddingTop }}>
        <ToastContainer position={toast.POSITION.TOP_LEFT} className="toastify-container" toastClassName="toastify-toast" />
        <ErrorBoundary>
          <Header
            user={props.user}
            isAuthenticated={props.isAuthenticated}
            isAdmin={props.isAdmin}
            isReceptionist={props.isReceptionist}
            isDoctor={props.isDoctor}
            isNurse={props.isNurse}
            isMarketing={props.isMarketing}
            isManager={props.isManager}
            isOperationsDirector={props.isOperationsDirector}
            isBranchBossDoctor={props.isBranchBossDoctor}
            isConsultant={props.isConsultant}
            isB2CMarketer={props.isB2CMarketer}
            currentLocale={props.currentLocale}
            onLocaleChange={props.setLocale}
            ribbonEnv={props.ribbonEnv}
            isInProduction={props.isInProduction}
            isSwaggerEnabled={props.isSwaggerEnabled}
          />
        </ErrorBoundary>
        <div className="container-fluid view-container" id="app-view-container">
          <Card className="jh-card">
            <ErrorBoundary>
              <AppRoutes />
            </ErrorBoundary>
          </Card>
          <Footer />
        </div>
      </div>
    </Router>
  );
};

const mapStateToProps = ({ authentication, applicationProfile, locale }: IRootState) => ({
  currentLocale: locale.currentLocale,
  user: authentication.account.login,
  isAuthenticated: authentication.isAuthenticated,
  isAdmin: authentication.isAdmin,
  isReceptionist: authentication.isReceptionist,
  isDoctor: authentication.isDoctor,
  isNurse: authentication.isNurse,
  isMarketing: authentication.isMarketing,
  isManager: authentication.isManager,
  isOperationsDirector: authentication.isOperationsDirector,
  isBranchBossDoctor: authentication.isBranchBossDoctor,
  isConsultant: authentication.isConsultant,
  isB2CMarketer: authentication.isB2CMarketer,
  ribbonEnv: applicationProfile.ribbonEnv,
  isInProduction: applicationProfile.inProduction,
  isSwaggerEnabled: applicationProfile.isSwaggerEnabled,
});

const mapDispatchToProps = { setLocale, getSession, getProfile };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(hot(module)(App));

import './header.scss';

import React, { useState } from 'react';
import { Translate, Storage } from 'react-jhipster';
import { Navbar, Nav, NavItem, NavLink, NavbarToggler, NavbarBrand, Collapse } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import {NavLink as Link} from 'react-router-dom';
import LoadingBar from 'react-redux-loading-bar';

import {Home, Brand, Customer, Payment, Booking, Employee, Consult, UserInformation} from './header-components';
import { AdminMenu, EntitiesMenu, AccountMenu, LocaleMenu } from '../menus';
import MenuItem from "app/shared/layout/menus/menu-item";
import {WebContentMenu} from "app/shared/layout/menus/web-content";
import {MaterialMenu} from "app/shared/layout/menus/material";

export interface IHeaderProps {
  user: string;
  isAuthenticated: boolean;
  isAdmin: boolean;
  // isCustomer: boolean;
  isReceptionist: boolean;
  isDoctor: boolean;
  isNurse: boolean;
  isMarketing: boolean;
  isManager: boolean;
  isOperationsDirector:boolean;
  isBranchBossDoctor:boolean;
  isConsultant:boolean;

  ribbonEnv: string;
  isInProduction: boolean;
  isSwaggerEnabled: boolean;
  currentLocale: string;
  onLocaleChange: Function;
}

const Header = (props: IHeaderProps) => {
  const [menuOpen, setMenuOpen] = useState(false);

  const handleLocaleChange = event => {
    const langKey = event.target.value;
    Storage.session.set('locale', langKey);
    props.onLocaleChange(langKey);
  };

  const renderDevRibbon = () =>
    props.isInProduction === false ? (
      <div className="ribbon dev">
        <a href="">
          <Translate contentKey={`global.ribbon.${props.ribbonEnv}`} />
        </a>
      </div>
    ) : null;

  const toggleMenu = () => setMenuOpen(!menuOpen);

  /* jhipster-needle-add-element-to-menu - JHipster will add new menu items here */

  return (
    <div id="app-header">
      {renderDevRibbon()}
      <LoadingBar className="loading-bar" />
      <Navbar dark expand="sm" fixed="top" className="jh-navbar">
        <NavbarToggler aria-label="Menu" onClick={toggleMenu} />
        <Brand />
        <UserInformation user={props.user}/>
        <Collapse isOpen={menuOpen} navbar>
          <Nav id="header-tabs" className="ml-auto" navbar>
            {isAuthenticated && (isAdmin
            || isConsultant || isReceptionist)
              && <Customer />}
            {isAuthenticated && (isAdmin
              || isManager)
              && <Employee />}
            {isAuthenticated && (isAdmin
            || isOperationsDirector || isDoctor || isBranchBossDoctor || isDoctor || isReceptionist)
              && <Booking />}
            {isAuthenticated && (isAdmin
            || isDoctor || isConsultant)
              && <Consult />}
            {isAuthenticated && (isAdmin
            || isReceptionist)
              && <Payment />}
            {isAuthenticated && (isAdmin
            || isMarketing)
              && <WebContentMenu />}
            {isAuthenticated && (isAdmin
            || isReceptionist)
              && <MaterialMenu />}
            {isAuthenticated && isAdmin
              && <AdminMenu showSwagger={props.isSwaggerEnabled} />}
            {/*{isAuthenticated && <EntitiesMenu />}*/}

            <LocaleMenu currentLocale={props.currentLocale} onClick={handleLocaleChange} />
            <AccountMenu isAuthenticated={props.isAuthenticated} />
          </Nav>
        </Collapse>
      </Navbar>
    </div>
  );
};

export default Header;

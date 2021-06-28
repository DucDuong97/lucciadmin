import React from 'react';
import { Translate } from 'react-jhipster';

import { NavItem, NavLink, NavbarBrand } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import appConfig from 'app/config/constants';

export const BrandIcon = props => (
  <div {...props} className="brand-icon">
    <img src="content/images/logo-jhipster.png" alt="Logo" />
  </div>
);

export const Brand = props => (
  <NavbarBrand tag={Link} to="/" className="brand-logo">
    <BrandIcon />
    <span className="brand-title">
      <Translate contentKey="global.title">Lucciadmin</Translate>
    </span>
    <span className="navbar-version">{appConfig.VERSION}</span>
  </NavbarBrand>
);

export const Home = props => (
  <NavItem>
    <NavLink tag={Link} to="/" className="d-flex align-items-center">
      <FontAwesomeIcon icon="home" />
      <span>
        <Translate contentKey="global.menu.home">Home</Translate>
      </span>
    </NavLink>
  </NavItem>
);

export const Customer = props => (
  <NavItem>
    <NavLink tag={Link} to="/customer" className="d-flex align-items-center">
      <span>
        <Translate contentKey="global.menu.entities.customer" />
      </span>
    </NavLink>
  </NavItem>
);

export const Employee = props => (
  <NavItem>
    <NavLink tag={Link} to="/employee" className="d-flex align-items-center">
      <span>
        <Translate contentKey="global.menu.entities.employee" />
      </span>
    </NavLink>
  </NavItem>
);

export const Booking = props => (
  <NavItem>
    <NavLink tag={Link} to="/booking" className="d-flex align-items-center">
      <span>
        <Translate contentKey="global.menu.entities.booking" />
      </span>
    </NavLink>
  </NavItem>
);

export const Payment = props => (
  <NavItem>
    <NavLink tag={Link} to="/payment" className="d-flex align-items-center">
      <span>
        <Translate contentKey="global.menu.entities.payment" />
      </span>
    </NavLink>
  </NavItem>
);

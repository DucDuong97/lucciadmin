import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const WebContentMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.web-content.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon={null} to="/service-item">
      <Translate contentKey="global.menu.web-content.serviceItem" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/pricing-card">
      <Translate contentKey="global.menu.entities.pricingCard" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/pricing-content">
      <Translate contentKey="global.menu.entities.pricingContent" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/process">
      <Translate contentKey="global.menu.entities.process" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/video">
      <Translate contentKey="global.menu.entities.video" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/blog">
      <Translate contentKey="global.menu.web-content.blog" />
    </MenuItem>
    <MenuItem icon={null} to="/achievement">
      <Translate contentKey="global.menu.web-content.achievement" />
    </MenuItem>
    <MenuItem icon={null} to="/customer-review">
      <Translate contentKey="global.menu.web-content.customerReview" />
    </MenuItem>
    <MenuItem icon={null} to="/singleton-content">
      <Translate contentKey="global.menu.web-content.singletonContent" />
    </MenuItem>
    <MenuItem icon={null} to="/policy">
      <Translate contentKey="global.menu.web-content.policy" />
    </MenuItem>
    <MenuItem icon={null} to="/branch">
      <Translate contentKey="global.menu.entities.branch" />
    </MenuItem>
    <MenuItem icon={null} to="/img-url">
      <Translate contentKey="global.menu.entities.imgUrl" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add web-content to the menu here */}
  </NavDropdown>
);
